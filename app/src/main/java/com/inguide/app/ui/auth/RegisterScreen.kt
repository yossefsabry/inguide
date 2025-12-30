package com.inguide.app.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.inguide.app.ui.theme.DesignSystem
import androidx.compose.ui.res.painterResource
import com.inguide.app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
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
            
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(DesignSystem.Dimensions.PaddingSmall))
            
            Text(
                text = "Join InGuide today",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Full Name") },
                leadingIcon = {
                    Icon(Icons.Outlined.Person, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = DesignSystem.Shapes.InputShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            )
            
            Spacer(modifier = Modifier.height(DesignSystem.Dimensions.PaddingMedium))
            
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
            
            Spacer(modifier = Modifier.height(DesignSystem.Dimensions.PaddingMedium))
            
            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = DesignSystem.Shapes.InputShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            )
            
            Spacer(modifier = Modifier.height(DesignSystem.Dimensions.PaddingMedium))
            
            // Confirm Password Field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirm Password") },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
            
            // Register Button
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        delay(1000)
                        onRegisterSuccess()
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
                enabled = !isLoading,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Text(
                    text = if (isLoading) "Creating Account..." else "Register",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Login Link
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(DesignSystem.Dimensions.PaddingSmall))
                TextButton(onClick = onNavigateToLogin) {
                    Text("Sign In", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    }
}
