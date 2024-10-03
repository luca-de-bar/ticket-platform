'use strict'

//ev.preventDefault();
const form = document.getElementById('login-form')

form.addEventListener('submit',function (ev){
    ev.preventDefault()
})

//Form Elements
const usernameInput = document.getElementById('username')
const passwordInput = document.getElementById('password')
const submit=document.getElementById('submit')


submit.addEventListener('click', function(){
    if(usernameInput.value.trim() === ""){
        usernameInput.classList.add('!border-red-500')
    }
    console.log("Ciaooo")
})
