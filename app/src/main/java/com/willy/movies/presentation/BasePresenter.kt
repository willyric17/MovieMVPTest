package com.willy.movies.presentation

interface BasePresenter<in V> {
    fun attach(view: V)
    fun detach()
}