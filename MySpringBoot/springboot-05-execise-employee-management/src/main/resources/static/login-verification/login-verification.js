var oUser = document.getElementsByClassName('user')[0],
    oKey = document.getElementsByClassName('key')[0],
    oBtn = document.getElementsByTagName('button')[0],
    oError = document.getElementsByClassName('error')[0],
    oUl = document.getElementsByTagName('ul')[0];

    regMobile = /^[1]\d{10}$/g;
    regEmail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/g;
    regKey = /^[A-Za-z]\w{7,}/g;

oBtn.addEventListener('click', function () {
    var username = oUser.value;
    var password = oKey.value;
    console.log();
    if((regMobile.test(username) || regEmail.test(username)) && regKey.test(password)) {
        oBtn.innerText = '登录中...';
    }else {
        oError.classList.remove('hidden');
    }
})

oUl.addEventListener('click', function() {
    oBtn.innerText = '登录';
    oError.classList.add('hidden');
})
