package org.d3if3044.wanderlist.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.d3if3044.wanderlist.R

@Composable
fun ImageDialog(
    bitmap: Bitmap?,
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String, String) -> Unit
) {
    var tujuan by remember { mutableStateOf("") }
    var kendaraan by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }

    val radioOptions = listOf(
        stringResource(id = R.string.transport1),
        stringResource(id = R.string.transport2),
    )
    val radioOptions2 = listOf(
        stringResource(id = R.string.transport3),
        stringResource(id = R.string.transport4)
    )

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(modifier = Modifier.padding(16.dp), shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
                OutlinedTextField(
                    value = tujuan,
                    onValueChange = { tujuan = it },
                    label = {
                        Text(text = "Tujuan")
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )

                LazyRow {
                    item {
                        radioOptions.forEach { text ->
                            RadiOptions(
                                label = text,
                                isSelected = kendaraan == text,
                                modifier = Modifier
                                    .selectable(
                                        selected = kendaraan == text,
                                        onClick = {
                                            kendaraan = text
                                        },
                                        role = Role.RadioButton
                                    )
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                    item {
                        radioOptions2.forEach { text ->
                            RadiOptions(
                                label = text,
                                isSelected = kendaraan == text,
                                modifier = Modifier
                                    .selectable(
                                        selected = kendaraan == text,
                                        onClick = {
                                            kendaraan = text
                                        },
                                        role = Role.RadioButton
                                    )
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = deskripsi,
                    onValueChange = { deskripsi = it },
                    label = {
                        Text(text = "Deskripsi")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Batal")
                }
                OutlinedButton(
                    onClick = { onConfirmation(tujuan, kendaraan, deskripsi) },
                    enabled = tujuan.isNotEmpty() && kendaraan.isNotEmpty() && deskripsi.isNotEmpty(),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Simpan")
                }
            }
        }
    }
}

@Composable
fun RadiOptions(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
