package com.example.gamevault.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gamevault.database.GameVaultRepository;
import com.example.gamevault.database.entities.GameVault;

import java.util.List;

public class GameVaultViewModel extends AndroidViewModel {
    private final GameVaultRepository repository;

    public GameVaultViewModel(Application application){
        super(application);
        repository = GameVaultRepository.getRepository(application);
    }

    public LiveData<List<GameVault>> getAllLogsById(int userId) {
        return repository.getAllLogsByUserIdLiveData(userId);
    }

    public void insert(GameVault log){
        repository.insertgamevault(log);
    }
}
