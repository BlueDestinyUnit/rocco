const searchForm = document.getElementById('searchForm');
const hotelSection = document.getElementById('hotelSection');

searchForm.onsubmit = (e) => {
    e.preventDefault();
    const propertyRegion = searchForm['propertyRegion'].value;
    const arrivalDate = searchForm['arrivalDate'].value + ' 14:00:00';
    const departureDate = searchForm['departureDate'].value + ' 12:00:00';
    const roomCount = searchForm['roomCount'].value;
    const customers = searchForm['customers'].value;
    const queryString = `propertyRegion=${propertyRegion}&arrivalDate=${arrivalDate}&departureDate=${departureDate}&roomCount=${roomCount}&customers=${customers}`;

    // const queryString = `propertyRegion=${propertyRegion}&arrivalDate=${arrivalDate} 14:00:00&departureDate=${departureDate} 12:00:00&capacity=${capacity}&customers=${customers}`;
    console.log(queryString);
    fetch(`/searchReservation?${queryString}`) // URL에 쿼리 문자열 추가
        .then((response) => {
            console.log("Response status:", response.status);
            console.log("Response headers:", response.headers);
            return response.json();
        })
        .then((data) => {
            console.log(typeof data);
            console.log(typeof data['list']);
            console.log(data['list']);
            const hotels = JSON.parse(data['list']);


            hotelSection.innerHTML = '';
            for (const orginHotel of hotels){
                console.log(orginHotel)
                const inner = JSON.parse(orginHotel['property']);

                const itemEl = new DOMParser().parseFromString(`
                <div class="hotelItem">
                    <div class="hotelImg">
                        <a href="">
                           <img src="" alt="없어요">
                        </a>
                    </div>
                    <div class="hotelContent">
                        <div>${inner.name}</div>
                        <div>${inner.grade}</div>
                        <div>${inner.intro}</div>
                    </div>
                </div>`, "text/html").querySelector('div');
                hotelSection.append(itemEl);
            }
        }
        )
        .catch((error) => console.error("Error fetching data:", error));
}

function submit() {

}