'use strict'

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('login-form')
//Form Elements
    const usernameInput = document.getElementById('username')
    const passwordInput = document.getElementById('password')
    const userFeeback = document.getElementById('username-invalid-feedback')
    const passwordFeeback = document.getElementById('password-invalid-feedback')
    const submit=document.getElementById('submit')

    function validate() {
        form.addEventListener('submit',function (ev){
            //If inputs are empty or null
            if(usernameInput.value.trim() === ""){
                //prevent submit
                ev.preventDefault()

                //invalid feedbacks
                usernameInput.classList.add("border-red-500")
                userFeeback.innerHTML = "Perfavore, inserisci un username"
            }
            if (passwordInput.value.trim() === ""){
                //prevent submit
                ev.preventDefault()

                //invalid feedbacks
                passwordInput.classList.add("border-red-500")
                passwordFeeback.innerHTML = "Perfavore, inserisci una password"
            }
        })
    }

    function usernameInputCheckState(){
        usernameInput.addEventListener('change', function () {
            //username input
            if(usernameInput.value.trim() !== ""){
                //Remove invalid feedbacks
                usernameInput.classList.remove("border-red-500")
                userFeeback.innerHTML=""
            }
        })
    }

    function passwordInputCheckState(){
        passwordInput.addEventListener('change', function () {
            //username input
            if(passwordInput.value.trim() !== ""){
                //Remove invalid feedbacks
                passwordInput.classList.remove("border-red-500")
                passwordFeeback.innerHTML=""
            }
        })
    }

    usernameInputCheckState()
    passwordInputCheckState()
    validate()
});
