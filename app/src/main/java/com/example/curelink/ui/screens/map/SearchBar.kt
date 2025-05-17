package com.example.curelink.ui.screens.map

import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import timber.log.Timber

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onPlaceSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    AndroidView(
        factory = {
            AutoCompleteTextView(it).apply {
                hint = "Search for a place"
                setTextColor(textColor.toArgb())
                setHintTextColor(textColor.copy(alpha = 0.6f).toArgb())
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                threshold = 1

                val adapter = ArrayAdapter<String>(it, android.R.layout.simple_dropdown_item_1line)
                val placesClient = Places.createClient(context)
                val sessionToken = AutocompleteSessionToken.newInstance()
                setAdapter(adapter)

                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val query = s?.toString().orEmpty()
                        if (query.isNotEmpty()) {
                            val request = FindAutocompletePredictionsRequest.builder()
                                .setSessionToken(sessionToken)
                                .setQuery(query)
                                .build()

                            placesClient.findAutocompletePredictions(request)
                                .addOnSuccessListener { response ->
                                    adapter.clear()
                                    response.autocompletePredictions.forEach { prediction ->
                                        adapter.add(prediction.getFullText(null).toString())
                                    }
                                    adapter.notifyDataSetChanged()
                                }
                                .addOnFailureListener(){e->
                                    Timber.tag("Places").e(e, "Prediction fetch failed")
                                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            adapter.clear()
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

                setOnItemClickListener { _, _, position, _ ->
                    val selectedPlace = adapter.getItem(position)
                    selectedPlace?.let { onPlaceSelected(it) }
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}


