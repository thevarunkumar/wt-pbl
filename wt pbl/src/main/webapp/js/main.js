function showFormError(message) {
    alert(message);
    return false;
}

function validateLoginForm(form) {
    if (!form.email.value.trim() || !form.password.value.trim()) {
        return showFormError("Please enter both email and password.");
    }
    return true;
}

function validateRegisterForm(form) {
    if (!form.name.value.trim() || !form.email.value.trim() || !form.password.value.trim() || !form.role.value.trim()) {
        return showFormError("Please fill in all registration fields.");
    }
    if (form.password.value.trim().length < 4) {
        return showFormError("Password should be at least 4 characters for this demo project.");
    }
    return true;
}

function validateRequestForm(form) {
    if (!form.title.value.trim() || !form.description.value.trim() || !form.location.value.trim() || !form.contact.value.trim()) {
        return showFormError("Please complete all request fields.");
    }
    return true;
}

function validateFeedbackForm(form) {
    const requestId = Number(form.requestId.value);
    const rating = Number(form.rating.value);
    if (!requestId || requestId < 1) {
        return showFormError("Please enter a valid request ID.");
    }
    if (!rating || rating < 1 || rating > 5) {
        return showFormError("Please enter a rating from 1 to 5.");
    }
    return true;
}
