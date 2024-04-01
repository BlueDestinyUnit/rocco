const searchForm = document.getElementById('searchForm');
console.log(searchForm);

searchForm.onsubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(searchForm);
    // fetch("./searchTest")
    //     .then((response) => response.json())
    //     .then((data) => console.log(data));
    const xhr = new XMLHttpRequest();
    // const formData = new FormData();

    xhr.onreadystatechange = function () {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            alert("알 수없는 오류가 발생했습니다.")
            return;
        }
        const responseObject = JSON.parse(xhr.responseText);

    }
    xhr.open('GET','/searchTest');
    xhr.send();
}