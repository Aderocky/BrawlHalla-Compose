package com.example.brawlhallahero.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brawlhallahero.data.BrawlRepository
import com.example.brawlhallahero.model.Brawl
import com.example.brawlhallahero.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavViewModel(
    private val repository: BrawlRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Brawl>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Brawl>>>
        get() = _uiState

    fun getAddedFavBrawl() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedBrawl()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { brawl ->
                    _uiState.value = UiState.Success(brawl)

                }

        }
    }

    fun cekFavBrawl(brawlId: Long) {
        viewModelScope.launch {
            repository.updateBrawl(brawlId)
        }
    }
}