HTMLElement.INVALID_CLASS_NAME = '-invalid';
HTMLElement.VISIBLE_CLASS_NAME = '-visible';

HTMLElement.prototype.show = function () {
    this.classList.add(HTMLElement.VISIBLE_CLASS_NAME);
    return this;
}

HTMLElement.prototype.hide = function () {
    this.classList.remove(HTMLElement.VISIBLE_CLASS_NAME);
    return this;
}

HTMLElement.prototype.block = function () {
    this.style.display = 'block';
    return this;
}
HTMLElement.prototype.none = function () {
    this.style.display = 'none';
    return this;
}

const cover = document.getElementById('cover');

cover.show = function (onclick) {
    this.onclick = onclick;
    this.classList.add(HTMLElement.VISIBLE_CLASS_NAME);
    return this;
}

const userDialog = document.getElementById('userDialog');


function loadUserDialog(){
    cover.show();
    userDialog.block();
}



const loginCaller = document.body.querySelector('[rel="showLoginCaller"]');
const registerCaller = document.body.querySelector('[rel="showRegisterCaller"]');

console.log(loginCaller);


loginCaller.onclick = function () {
    loadUserDialog();
}
registerCaller.onclick = function () {
    loadUserDialog();
}



