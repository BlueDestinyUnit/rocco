
const logoutButton = document.getElementById('logoutButton');



logoutButton.onclick = function (e) {
    e.preventDefault();
    fetch('/user/logout/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            console.log("1111")
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // 서버로부터 받은 응답을 처리합니다.
            console.log(data);
        })
        .catch(error => {
            // fetch 중에 오류가 발생한 경우 처리합니다.
            console.error('There has been a problem with your fetch operation:', error);
            // 여기서 서버 오류인지 네트워크 오류인지 판단하고 적절한 조치를 취할 수 있습니다.
        });
}

