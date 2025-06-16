package com.tatanstudios.eltuncazodirecciones.extras

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Extension function to create a DataStore instance
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class TokenManager(private val context: Context) {

    // Claves
    private val ID_KEY = stringPreferencesKey("ID")

    // Guardar ID en DataStore
    suspend fun saveID(id: String) {
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = id
        }
    }

    // Leer ID como Flow
    val idUsuario: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[ID_KEY] ?: ""
        }

    // Eliminar ID
    suspend fun deletePreferences() {
        context.dataStore.edit { preferences ->
            preferences.remove(ID_KEY)
        }
    }
}




// UTILIZADO PARA EJECUTAR 1 VEZ LAS PETICIONES RETROFIT
class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}