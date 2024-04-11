const searchForm = document.getElementById('searchForm');

searchForm.onsubmit = (e) => {
    e.preventDefault();
    const propertyRegion = searchForm['propertyRegion'].value;
    const arrivalDate = searchForm['arrivalDate'].value + ' 14:00:00';
    const departureDate = searchForm['departureDate'].value + ' 12:00:00';
    const capacity =searchForm['capacity'].value;
    const customers =searchForm['customers'].value;
    const queryString = `propertyRegion=${propertyRegion}&arrivalDate=${arrivalDate}&departureDate=${departureDate}&capacity=${capacity}&customers=${customers}`;

    // const queryString = `propertyRegion=${propertyRegion}&arrivalDate=${arrivalDate} 14:00:00&departureDate=${departureDate} 12:00:00&capacity=${capacity}&customers=${customers}`;
    console.log(queryString);
    fetch(`/searchReservation?${queryString}`) // URL에 쿼리 문자열 추가
        .then((response) => {
            console.log("Response status:", response.status);
            console.log("Response headers:", response.headers);
            return response.json();
        })
        .then((data) => {console.log(data);
                const jsonDataString = JSON.stringify(data)
                const jsondate = data['message'];
                console.log(jsonDataString);
            }
        )
        .catch((error) => console.error("Error fetching data:", error));

}

function submit() {

}