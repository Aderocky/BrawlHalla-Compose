package com.example.brawlhallahero.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brawlhallahero.data.BrawlRepository
import com.example.brawlhallahero.model.Brawl
import com.example.brawlhallahero.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: BrawlRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Brawl>> =
        MutableStateFlow(UiState.Loading)
    val resultState: StateFlow<UiState<Brawl>>
        get() = _uiState

    fun getBrawlById(brawlId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getBrawlById(brawlId))
        }
    }

}