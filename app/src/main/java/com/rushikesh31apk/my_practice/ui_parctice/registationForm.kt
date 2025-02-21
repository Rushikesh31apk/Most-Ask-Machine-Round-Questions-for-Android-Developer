package com.rushikesh31apk.my_practice.ui_parctice


import android.app.DatePickerDialog
import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rushikesh31apk.my_practice.AppNavigation.BottomNavigationBar
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {



    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Select Gender") }
    var selectedState by remember { mutableStateOf("Select State") }

    // Error states
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }
    var dobError by remember { mutableStateOf<String?>(null) }
    var stateError by remember { mutableStateOf<String?>(null) }

    var selectedIndex by remember { mutableStateOf(3) }

    val statesList = listOf("Maharashtra", "Karnataka", "Gujarat", "Delhi", "Tamil Nadu")

    Scaffold(

        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                 modifier = Modifier.fillMaxWidth().padding(start = 100.dp), title = {
                Text(
                    "Register Form",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 30.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { InputField("Name", name, { name = it }, Icons.Default.Person, nameError) }
            item { InputField("Email", email, { email = it }, Icons.Default.Email, emailError) }
            item { InputField("Password", password, { password = it }, Icons.Default.Lock, passwordError, true) }
            item {
                InputField(
                    "Phone",
                    phone,
                    { phone = it },
                    Icons.Default.Phone,
                    phoneError,
                    keyboardType = KeyboardType.Number
                )
            }
            item {
                InputField(
                    "Address",
                    address,
                    { address = it },
                    Icons.Default.Home,
                    addressError
                )
            }
            item {
                DropdownMenuField(
                    "Select Gender",
                    gender,
                    listOf("Male", "Female", "Other"),
                    { gender = it },
                    null
                )
            }
            item { DatePickerField("Date of Birth", dob, { dob = it }, dobError) }
            item {
                DropdownMenuField(
                    "Select State",
                    selectedState,
                    statesList,
                    { selectedState = it },
                    stateError
                )
            }
            item {
                Button(
                    onClick = {
                        val isValid = validateInputs(
                            name,
                            email,
                            password,
                            phone,
                            address,
                            dob,
                            selectedState,
                            { nameError = it },
                            { emailError = it },
                            { passwordError = it },
                            { phoneError = it },
                            { addressError = it },
                            { dobError = it },
                            { stateError = it }
                        )
                        if (isValid) {
                            scope.launch { snackbarHostState.showSnackbar("Registration Successful!") }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Register") }
            }
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Icon(icon, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            isError = errorMessage != null
        )
        if (errorMessage != null) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
    }
}

@Composable
fun DatePickerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth -> onValueChange("$dayOfMonth/${month + 1}/$year") },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column {
        OutlinedTextField(
            value = value,

            onValueChange = {onValueChange(it)},
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePicker.show() },
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Select Date",
                    Modifier.clickable { datePicker.show() })
            },
            isError = errorMessage != null
        )
        if (errorMessage != null) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    errorMessage: String?
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {onOptionSelected(it)},
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // Ensures dropdown aligns with TextField
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    Modifier.clickable {if(!expanded) expanded = false else expanded = true})
            },
            isError = errorMessage != null,
            readOnly = true // Prevents manual input
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
    if (errorMessage != null) {
        Text(errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
    }
}

fun validateInputs(
    name: String,
    email: String,
    password: String,
    phone: String,
    address: String,
    dob: String,
    state: String,
    setNameError: (String?) -> Unit,
    setEmailError: (String?) -> Unit,
    setPasswordError: (String?) -> Unit,
    setPhoneError: (String?) -> Unit,
    setAddressError: (String?) -> Unit,
    setDobError: (String?) -> Unit,
    setStateError: (String?) -> Unit
): Boolean {
    var isValid = true

    if (name.isBlank()) {
        setNameError("Name cannot be empty")
        isValid = false
    } else setNameError(null)

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        setEmailError("Invalid email format")
        isValid = false
    } else setEmailError(null)

    if (password.length < 6) {
        setPasswordError("Password must be at least 6 characters")
        isValid = false
    } else setPasswordError(null)

    if (!phone.matches(Regex("\\d{10}"))) {
        setPhoneError("Phone number must be 10 digits")
        isValid = false
    } else setPhoneError(null)

    if (address.isBlank()) {
        setAddressError("Address cannot be empty")
        isValid = false
    } else setAddressError(null)

    if (dob.isBlank()) {
        setDobError("Date of Birth cannot be empty")
        isValid = false
    } else setDobError(null)

    if (state == "Select State") {
        setStateError("Please select a state")
        isValid = false
    } else setStateError(null)

    return isValid
}
