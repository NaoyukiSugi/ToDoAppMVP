package com.example.todoappmvp

interface BaseView<T> {
    fun setPresenter(presenter: T)
}
