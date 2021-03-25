package com.vn.apps.newz.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.vn.apps.newz.data.repository.NewsRepository

class NewsListViewModel @ViewModelInject constructor(
    repository: NewsRepository
) : ViewModel() {

    val topHeadLines = repository.getTopHeadLines()
}
