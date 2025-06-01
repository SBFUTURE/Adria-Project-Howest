const url = "https://project-2.ti.howest.be/2023-2024/group-13/";

export const toAddress = async (LatLng) => {
    return await fetchData(`https://api.myptv.com/geocoding/v1/locations/by-position/${LatLng.lat}/${LatLng.lng}?language=en&apiKey=RVVfZmViODk5MDdjMjcwNGUwM2E5NzcyYWE1MTY2ZmY1MTU6MGRhMGNiNzAtNTgyOC00ODcwLWIwZDQtODU4MDM4NzMwZjhl`)
        .then(data => {
            return data.locations[0];
        })
        .catch(error => console.error('Error:', error));
}

export const getAddressInfo = async (address) => {
    return await fetchData(`https://api.myptv.com/geocoding/v1/locations/by-text?searchText=${address}&apiKey=RVVfZmViODk5MDdjMjcwNGUwM2E5NzcyYWE1MTY2ZmY1MTU6MGRhMGNiNzAtNTgyOC00ODcwLWIwZDQtODU4MDM4NzMwZjhl`, "GET").then(response => {
        return response.locations[0].address;
    }).catch(error => console.error(error));
}

export const fetchUserInfo = async (adriaId) => {
    const requestURL = `${url}/api/user/info`;
    const requestHeader = {
        Authorization: adriaId
    }
    await fetchData(requestURL, "GET", null, requestHeader).then(data => {
        localStorage.setItem("isDriver", JSON.stringify(data.carInfo != null));
        localStorage.setItem("userId", JSON.stringify(data.userId));
        localStorage.setItem("name", JSON.stringify(data.name));
    }).catch(error => console.error(error.message));
}

export const fetchReviews = async (userId, adriaId, rideId, name) => {
    const requestURL = `${url}/api/user/info/${userId}`;
    const requestHeader = {
        Authorization: adriaId
    }
    return await fetchData(requestURL, "GET", null, requestHeader).then(data => {
        const result = data.reviews.filter(review => review.rideId === rideId && review.reviewer === name);
        if (result.length > 0) {
            const review = result[0].reviewScore;
            return review;
        } else {
            return -1;
        }
    }).catch(error => console.error(error.message));
}

export const createRide = async (adriaId, source, destination, price, seats, date) => {
    const geoDataSource = await getAddressInfo(source);
    const geoDataDestination = await getAddressInfo(destination);
    const requestURL = `${url}/api/ride/create`;
    const requestBody = {
        source: {
            city: geoDataSource.city,
            cityCode: geoDataSource.postalCode,
            street: geoDataSource.street,
            homeNumber: geoDataSource.houseNumber
        },
        destination: {
            city: geoDataDestination.city,
            cityCode: geoDataDestination.postalCode,
            street: geoDataDestination.street,
            homeNumber: geoDataDestination.houseNumber
        },
        price: parseInt(price),
        seats: parseInt(seats),
        date: date
    }
    const requestHeader = {
        Authorization: adriaId
    };
    await fetchData(requestURL, "POST", requestBody, requestHeader).then(response => {
    }).catch(error => console.error(error));
}

export const becomeDriver = async (adriaId, formData) => {
    const requestURL = `${url}/api/user/carinfo`;
    const requestBody = {
        carBrand: formData.brand,
        carModel: formData.modal,
        carBuildYear: parseInt(formData.year),
        carLicensePlate: formData.licensePlate
    }
    const requestHeader = {
        Authorization: adriaId
    }
    const result = await fetchData(requestURL, "POST", requestBody, requestHeader).then(data => {
        fetchUserInfo(adriaId);
        return data;
    }).catch(error => console.error(error.message));
}

export const toLatLng = async (address) => {
    return await fetchData(`https://api.myptv.com/geocoding/v1/locations/by-text?searchText=${address}&countryFilter=BE&apiKey=RVVfZmViODk5MDdjMjcwNGUwM2E5NzcyYWE1MTY2ZmY1MTU6MGRhMGNiNzAtNTgyOC00ODcwLWIwZDQtODU4MDM4NzMwZjhl`)
        .then(data => {
            return data;
        })
        .catch(error => error);
}

export const fetchRides = async (adriaId, source, destination, seats, date) => {
    const requestHeader = {
        Authorization: adriaId
    }
    const rides = await fetchData(`${url}/api/rides`, "GET", null, requestHeader)
        .then(data => {
            return data;
        })
        .catch(error => error);

    const filtered = filterRides(rides, source, destination, seats, date);
    return filtered;
}

export const getUserRides = async (adriaId) => {
    const requestHeader = {
        Authorization: adriaId
    }
    return await fetchData(`${url}/api/user/rides`, "GET", null, requestHeader)
        .then(data => {
            return data;
        })
        .catch(error => { return error });
}

export const planRide = async (adriaId, startPoint, endPoint, date, seats, driverId) => {
    const dateObject = new Date(date.value);
    const year = dateObject.getFullYear();
    const month = dateObject.getMonth() + 1;
    const day = dateObject.getDate();

    const requestHeader = {
        Authorization: adriaId
    }
    const requestBody = {
        startPoint,
        endPoint,
        date: {
            year: year,
            month: month,
            day: day
        },
        amount: parseInt(seats),
        driverId
    }
    return await fetchData(`${url}/api/ride/plan`, "POST", requestBody, requestHeader)
        .then(data => {
            return data;
        })
        .catch(error => { return error });
}

const filterRides = (rides, source, destination, seats, date) => {
    const filtered = rides.filter(ride =>
        ride.source.city.toLowerCase() === source.toLowerCase() &&
        ride.destination.city.toLowerCase() === destination.toLowerCase() &&
        ride.availableSeats >= seats &&
        ride.date == date.value
    );
    return filtered;
}

export const authenticate = async (adriaId) => {
    fetchUserInfo(adriaId);
}

export const reviewRide = async (adriaId, rideId, userId, reviewScore) => {
    const requestURL = `${url}/api/user/review`;

    const requestHeader = {
        Authorization: adriaId
    };

    const requestBody = {
        userId,
        reviewScore: reviewScore,
        rideId,

    };
    return await fetchData(requestURL, "POST", requestBody, requestHeader)
        .then(data => {
            return data;
        })
        .catch(error => { return error });

}
export const updateRideStatus = async (adriaId, rideId, status) => {
    const requestURL = `${url}/api/ride/update/${rideId}/${status}`;

    const requestHeader = {
        Authorization: adriaId
    }
    return await fetchData(requestURL, "PATCH", null, requestHeader)
        .then(data => {
            return data;
        })
        .catch(error => { return error });
}
const fetchData = async (url, method = "GET", body = null, headers = {}) => {
    const options = { method, headers: { 'Content-Type': 'application/json', ...headers }, body: body ? JSON.stringify(body) : null };
    const response = await fetch(url, options);
    return await response.json();
};

