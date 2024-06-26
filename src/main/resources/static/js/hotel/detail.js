const paymentModal = document.getElementById('paymentModal');
const paymentModalIndex = document.querySelectorAll('[data-index]');
const paymentReady = document.querySelector('[rel="paymentReady"]');
const rooms = document.getElementById('rooms')
const availableRooms = document.getElementById('availableRooms');


// form
const searchForm = document.getElementById('searchForm');
const customerRegisForm = document.getElementById('customerRegisForm');
const paymentRegisForm = document.getElementById('paymentRegisForm');

// 버튼
const returnButtons = document.querySelectorAll('[rel="returnButton"]');
const nextButton = document.querySelector('[rel="nextButton"]');

// 다이아로그 인덱스
let currentPaymentModalIndex = 1;
let checkdRoomsId = [];

function searchAvailableRooms() {
    const formData = {
        "hotelId":searchForm['hotelId'].value,
        "arrivalDate": `${searchForm['arrivalDate'].value}T14:00:00`,
        "departureDate": `${searchForm['departureDate'].value}T12:00:00`,
        "roomCount": searchForm['roomCount'].value,
        "customers": searchForm['customers'].value
    }

    fetch('/room/availableRooms', {
        method: 'PATCH',
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
            console.log(data['rooms']);
            const roomContainer = rooms.querySelector('.roomContainer');
            roomContainer.innerHTML='';

            data['rooms'].forEach(room => {

                const itemEl = new DOMParser().parseFromString(`
                <div class="roomItem">
                    <label>
                        <input name="checkbox" type="checkbox" value="${room.id}">
                            <div class="roomImg">
                                <img src='../room/thumbnail?index=${room.id}' alt="">
                            </div>
                            <div>
                                <div class="roomName">${room.name}</div>
                                <div class="roomCapacity" >${room.capacity}</div>
                                <div class="roomPrice" >${room.price}</div>
                            </div>
                    </label>
                </div>`,'text/html').querySelector('div');
                roomContainer.append(itemEl);
            });

        })
        .catch(error => {
            // fetch 중에 오류가 발생한 경우 처리합니다.
            console.error('There has been a problem with your fetch operation:', error);
            // 여기서 서버 오류인지 네트워크 오류인지 판단하고 적절한 조치를 취할 수 있습니다.
        });
}


searchAvailableRooms();


searchForm.onsubmit = (e) => {
    e.preventDefault();
    searchAvailableRooms();
}



// 다이아로그를 여는 함수
paymentReady.onclick = function (e) {
    cover.show();
    paymentModal.show();
    findDataHotelIndex();
    checkdRoomsId = checkedRooms();
}

// 다음 버튼
nextButton.onclick = function (e) {
    e.preventDefault();
    currentPaymentModalIndex += 1;
    findDataHotelIndex();
}

// 뒤로 가기 버튼
for (let i = 0; i < returnButtons.length; i++) {
    returnButtons[i].addEventListener('click', (e) => {
        currentPaymentModalIndex -=1;
        findDataHotelIndex();
    });
}

// 고객 정보 입력
customerRegisForm.onsubmit = function (e) {
    e.preventDefault();
    currentPaymentModalIndex = currentPaymentModalIndex + 1;
    findDataHotelIndex();
}

// 결제 입력
paymentRegisForm.onsubmit = function (e) {
    e.preventDefault();
    const formData = new FormData();
    formData.append('cardNum',paymentRegisForm['cardNum'].value);
    formData.append('cardType',paymentRegisForm['cardType'].value);
    formData.append('roomArray',checkdRoomsId);
    formData.append('hotelId',searchForm['hotelId'].value);
    formData.append('arrivalDate', `${searchForm['arrivalDate'].value}T14:00:00`);
    formData.append('departureDate', `${searchForm['departureDate'].value}T12:00:00`);
    formData.append('roomCount', searchForm['roomCount'].value);
    formData.append('customers', searchForm['customers'].value);
    formData.append('firstName',customerRegisForm['firstName'].value);
    formData.append('lastName',customerRegisForm['lastName'].value);
    formData.append('phone',customerRegisForm['phone'].value);

    fetch('/payment/', {
        method: 'POST',
        body: formData
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
    currentPaymentModalIndex = 1;
    cover.hide();
    paymentModal.hide();
}


// 다이아로그 순서를 정하는 함수
function findDataHotelIndex() {
    Array.from(paymentModalIndex).forEach(element => {
        const dataIndex = element.getAttribute('data-index');
        if(currentPaymentModalIndex === parseInt(dataIndex)){
            element.block();
        }else{
            element.none();
        }
    });
}

function checkedRooms() {
    const roomElList = document.getElementById('rooms').querySelectorAll('.roomItem');
    const checkedObject = [];
    const list = availableRooms.querySelector('.list');
    list.innerHTML= '';
    Array.from(roomElList).forEach(element => {
        const roomCheckbox = element.querySelector('input[name="checkbox"]');
        const isCheck = roomCheckbox.checked;
        if(isCheck === true){
            const roomId = roomCheckbox.value;
            const roomName = element.querySelector('.roomName').innerText;
            const roomCapacity = element.querySelector('.roomCapacity').innerText;
            const roomPrice = element.querySelector('.roomPrice').innerText;
            // checkedObject.push({"roomId":roomId,"roomName" : roomName, "roomCapacity" : roomCapacity, "roomPrice" : roomPrice});
            checkedObject.push(roomId);

            const itemEl = new DOMParser().parseFromString(`
                    <li class="item">
                        <img alt="" class="image"
                             src='../room/thumbnail?index=${roomId}'>
                        <div class="spec">
                            <span class="name-wrapper"><span class="name">${roomName}</span>
                                <span class="days">2박</span>
                            </span>
                            <span class="room-price">${roomPrice}</span>
                            <span class="misc">
                                <span class="container">${roomCapacity}</span>
                                <span class="review">
                                    <span class="text"></span>
                                    <span class="role">VIP전용</span>
                                </span>
                            </span>
                            <span class="button-container">
                                <button class="button" name="modify" type="button">취소</button>
                            </span>
                        </div>
                    </li>
            `, "text/html").querySelector('li');
            list.append(itemEl);
        }
    });
    return checkedObject;
}






