const userModal = document.getElementById('userModal');
const elementsWithUserModalIndex = document.querySelectorAll('[data-user-index]');
const loginForm = userModal.querySelector('#loginForm');


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
    console.log('fadfas')
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





