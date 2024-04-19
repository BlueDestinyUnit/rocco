const paymentDialog = document.getElementById('paymentDialog');
const elementsWithIndex = document.querySelectorAll('[data-index]');
const paymentReady = document.querySelector('[rel="paymentReady"]');
const rooms = document.getElementById('rooms').querySelectorAll('.roomItem');
const availableRooms = document.getElementById('availableRooms');


// form
const customerRegisForm = document.getElementById('customerRegisForm');
const paymentRegisForm = document.getElementById('paymentRegisForm');

// 버튼
const returnButtons = document.querySelectorAll('[rel="returnButton"]');
const cancleButton =document.querySelector('[rel="cancleButton"]');
const nextButton = document.querySelector('[rel="nextButton"]');

// 다이아로그 인덱스
let currentIndex = 1;

// 뒤로 가기 버튼
for (let i = 0; i < returnButtons.length; i++) {
    returnButtons[i].addEventListener('click', (e) => {
       currentIndex -=1;
       findDataIndex();
    });
}

// 다이아로그를 여는 함수
paymentReady.onclick = function (e) {
    cover.show();
    paymentDialog.show();
    findDataIndex();
    checkedRooms();
}

// 취소 버튼
cancleButton.onclick = function (e) {
    e.preventDefault();
    currentIndex = 1;
    cover.hide();
    paymentDialog.hide();
}

nextButton.onclick = function (e) {
    e.preventDefault();
    currentIndex += 1;
    findDataIndex();
}

// 고객 정보 입력
customerRegisForm.onsubmit = function (e) {
    e.preventDefault();
    currentIndex = currentIndex + 1;
    findDataIndex();
}

// 결제 입력
paymentRegisForm.onsubmit = function (e) {
    e.preventDefault();
    currentIndex = 1;
    cover.hide();
    paymentDialog.hide();
}


// 다이아로그 순서를 정하는 함수
function findDataIndex() {
    Array.from(elementsWithIndex).forEach(element => {
        const dataIndex = element.getAttribute('data-index');
        if(currentIndex === parseInt(dataIndex)){
            element.block();
        }else{
            element.none();
        }
    });
}

function checkedRooms() {
    const checkedObject = [];
    const list = availableRooms.querySelector('.list');
    list.innerHTML= '';
    Array.from(rooms).forEach(element => {
        const roomCheckbox = element.querySelector('input[name="checkbox"]');
        const isCheck = roomCheckbox.checked;
        if(isCheck == true){
            const roomId = roomCheckbox.value;
            const roomName = element.querySelector('.roomName').innerText;
            const roomCapacity = element.querySelector('.roomCapacity').innerText;
            const roomPrice = element.querySelector('.roomPrice').innerText;
            checkedObject.push({"roomId":roomId,"roomName" : roomName, "roomCapacity" : roomCapacity, "roomPrice" : roomPrice});

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
}






