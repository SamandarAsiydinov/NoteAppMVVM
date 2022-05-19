package com.example.noteappmvvm.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.snackBar(v: View, text: String) {
    Snackbar.make(v, text, Snackbar.LENGTH_LONG).show()
}
fun Fragment.toast(text: String) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_SHORT).show()
}