const userModal = document.getElementById('userModal');
const elementsWithUserModalIndex = document.querySelectorAll('[data-user-index]');
const loginForm = userModal.querySelector('#loginForm');
const registerForm = userModal.querySelector('#registerForm');
const addressWrapper = userModal.querySelector('.address-wrapper');

let userModalIndex = 1;

document.body.querySelectorAll('[rel="showLoginCaller"]').forEach(el => {
    el.onclick = function () {
        userModalIndex = 1;
        loadUserModal();
    }
});
document.body.querySelectorAll('[rel="showRegisterCaller"]').forEach(el => {
    el.onclick = function () {
        userModalIndex = 2;
        loadUserModal();
    }
});


function loadUserModal() {
    cover.show();
    userModal.show();
    findDataUserModalIndex()
}

function findDataUserModalIndex() {
    Array.from(elementsWithUserModalIndex).forEach(element => {
        const dataIndex = element.getAttribute('data-user-index');
        if (userModalIndex === parseInt(dataIndex)) {
            element.block();
        } else {
            element.none();
        }
    });
}


loginForm.onsubmit = function (e) {
    e.preventDefault();
    const formData = {
        username: loginForm['email'].value,
        password: loginForm['password'].value
        // 다른 필드들도 필요하다면 여기에 추가
    };


    fetch('/user/login/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // 서버로부터 받은 응답을 처리합니다.
            console.log(data);

            location.reload();
        })
        .catch(error => {
            // fetch 중에 오류가 발생한 경우 처리합니다.
            console.error('There has been a problem with your fetch operation:', error);
            // 여기서 서버 오류인지 네트워크 오류인지 판단하고 적절한 조치를 취할 수 있습니다.
        });
}

function logout(e) {
    e.preventDefault();
    fetch('/user/logout/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    }).then(data => {
        // 서버로부터 받은 응답을 처리합니다.
        console.log(data);
        location.reload();
    }).catch(error => {
        // fetch 중에 오류가 발생한 경우 처리합니다.
        console.error('There has been a problem with your fetch operation:', error);
        // 여기서 서버 오류인지 네트워크 오류인지 판단하고 적절한 조치를 취할 수 있습니다.
    });
}

function cancelUserModal() {
    userModalIndex = 1;
    userModal.hide();
    cover.hide();
}

registerForm['emailSend'].onclick = function (e) {
    e.preventDefault();
    const formData = {
        email: registerForm['email'].value
    };
    console.log(registerForm['email'].value)
    fetch('user/sendEmailCode', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log(data['result']);

        const [dTitle, dContent, dOnclick] = {
            failure: ['경고', '규칙에 맞게 이메일을 작성해주세요', () => registerForm['emailCode'].focus()],
            failure_duplicate_email: ['경고', '이미 쓰고 있는 이메일입니다.', () => {
                registerForm['email'].focus();
            }],
            success: ['알림', '이메일의 전송을 완료 했습니다.', () => {
                registerForm['email'].disable();
                registerForm['emailSend'].disable();
                registerForm['emailCode'].enable();
                registerForm['emailCode'].focus();
                registerForm['emailVerify'].enable();
            }]
        }[data.result] || ['경고', '서버가 예상치 못한 응답을 반환하였습니다. 잠시 후 다시 시도해 주세요.'];
        DialogObj.createSimpleOk(dTitle, dContent, dOnclick).show();
    })
    .catch(error => {
        // fetch 중에 오류가 발생한 경우 처리합니다.
        console.error('There has been a problem with your fetch operation:', error);
        // 여기서 서버 오류인지 네트워크 오류인지 판단하고 적절한 조치를 취할 수 있습니다.
    });
}


registerForm['emailVerify'].onclick = function (e) {
    e.preventDefault();
    const formData = {
        email: registerForm['emailCode'].value
    };
    fetch('user/emailVerify', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log(data)})
    .catch(error => {
        // fetch 중에 오류가 발생한 경우 처리합니다.
        console.error('There has been a problem with your fetch operation:', error);
        // 여기서 서버 오류인지 네트워크 오류인지 판단하고 적절한 조치를 취할 수 있습니다.
    });
}


registerForm['addressFind'].onclick = () =>  {
    addressWrapper.show();
    new daum.Postcode({
        width: '100%',
        height: '100%',
        oncomplete: (data) => {
            registerForm['addressPostal'].value = data['zonecode'];
            registerForm['addressPrimary'].value = data['address'];
            registerForm['addressSecondary'].focus();
            addressWrapper.hide();
        }
    }).embed(addressWrapper.querySelector(':scope > .dialog'));
};















