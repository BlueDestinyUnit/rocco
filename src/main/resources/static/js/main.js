const searchForm = document.getElementById('searchForm');
console.log(searchForm);


searchForm.onsubmit = (e) => {
    e.preventDefault();
    const propertyId = searchForm['propertyName'].value;
    const arrivalDate = searchForm['arrivalDate'].value;
    const arrivalTime = searchForm['arrivalTime'].value;
    const departureDate = searchForm['departureDate'].value;
    const departureTime = searchForm['departureTime'].value;


    const queryString = `propertyId=${propertyId}&arrivalDate=${arrivalDate} ${arrivalTime}&departureDate=${departureDate} ${departureTime}`;
    console.log(queryString);
    fetch(`/searchReservation?${queryString}`) // URL에 쿼리 문자열 추가
        .then((response) => {
            console.log("Response status:", response.status);
            console.log("Response headers:", response.headers);
            return response.json();
        })
        .then((data) => {console.log(data);
                const jsondate = data['message'];
                console.log(jsondate);
            }
        )
        .catch((error) => console.error("Error fetching data:", error));

    const xhr = new XMLHttpRequest();
}

function submit() {

}