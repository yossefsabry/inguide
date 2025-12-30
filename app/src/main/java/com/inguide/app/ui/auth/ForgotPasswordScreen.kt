package com.inguide.app.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.inguide.app.ui.theme.DesignSystem
import androidx.compose.ui.res.painterResource
import com.inguide.app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = DesignSystem.Dimensions.PaddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Title
            Text(
                text = "Forgot Password",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Enter your email to receive a reset code",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            if (isSuccess) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Code Sent!",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Please check your email inbox.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = DesignSystem.Shapes.ButtonShape,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Back to Sign In")
                }
            } else {
                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Student Email") },
                    leadingIcon = {
                        Icon(Icons.Outlined.Email, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    shape = DesignSystem.Shapes.InputShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Send Code Button
                Button(
                    onClick = {
                        scope.launch {
                            isLoading = true
                            delay(1500) // Simulate API call
                            isSuccess = true
                            isLoading = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = DesignSystem.Shapes.ButtonShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = !isLoading && email.isNotEmpty(),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        text = if (isLoading) "Sending..." else "Send Code",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                TextButton(onClick = onNavigateBack) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
