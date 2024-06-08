package dev.pan.stockmarket.util

/* Used for request operations with API.
On Success retrieve of data we just return data to user.
If error occurs we return error message to user and if we have data stored locally, for example, we can attach it with message
Loading state is used just to be placed at the beginning of request function*/
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)
}