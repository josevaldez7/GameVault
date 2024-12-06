package com.example.gamevault.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.gamevault.database.entities.GameVault;
import com.example.gamevault.MainActivity;
import com.example.gamevault.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GameVaultRepository {
    private final GameVaultDAO gameVaultDAO;
    private final UserDAO userDAO;
    private ArrayList<GameVault> allLogs;

    private static GameVaultRepository repository;

    private GameVaultRepository(Application application){
        GameVaultDataBase db = GameVaultDataBase.getDatabase(application);
        this.gameVaultDAO = db.gamevaultDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<GameVault>) this.gameVaultDAO.getAllRecords();
    }

    public static GameVaultRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<GameVaultRepository> future = GameVaultDataBase.databaseWriteExecutor.submit(
                new Callable<GameVaultRepository>() {
                    @Override
                    public GameVaultRepository call() throws Exception {
                        return new GameVaultRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting gamevaultRepository, thread error.");
        }
        return null;
    }

    public ArrayList<GameVault> getAllLogs() {
        Future<ArrayList<GameVault>> future = GameVaultDataBase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GameVault>>() {
                    @Override
                    public ArrayList<GameVault> call() throws Exception {
                        return (ArrayList<GameVault>) gameVaultDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all gamevaults in the repository");
        }
        return null;
    }

    public void insertgamevault(GameVault gameVault){
        GameVaultDataBase.databaseWriteExecutor.execute(() ->
        {
            gameVaultDAO.insert(gameVault);
        });
    }

    public void insertUser(User... user){
        GameVaultDataBase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<List<User>> getAllUsers() {
        return userDAO.getALlUsers();
    }

    public void deleteUser(User user) {
        GameVaultDataBase.databaseWriteExecutor.execute(() -> userDAO.deleteUser(user));
    }

    public void updateUser(User user) {
        GameVaultDataBase.databaseWriteExecutor.execute(() -> userDAO.update(user));
    }


    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<List<GameVault>> getAllLogsByUserIdLiveData(int loggedInUserId){
        return gameVaultDAO.getRecordsetUserIdLiveData(loggedInUserId);
    }

    @Deprecated
    public ArrayList<GameVault> getAllLogsByUserId(int loggedInUserId) {
        Future<ArrayList<GameVault>> future = GameVaultDataBase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GameVault>>() {
                    @Override
                    public ArrayList<GameVault> call() throws Exception {
                        return (ArrayList<GameVault>) gameVaultDAO.getRecordsByUserId(loggedInUserId);
                    }
                }
        );

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all gamevaults in the repository");
        }
        return null;
    }

}
