import axios from 'axios'

class CountryListService {

    getCountryList () {
        return axios.get('http://localhost:8080/init');
    }
}
export default new CountryListService ()