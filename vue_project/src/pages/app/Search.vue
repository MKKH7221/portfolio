<template>
    <div class="content">
        <dev class="condition" >
            <h4> search condition</h4>
            <form>
                <table>
                    <tr>
                        <th id="th_condition" >ID</th>
                        <td>
                            <input type="text" v-model="consId">
                        </td>
                        <th id="th_condition" >Name</th>
                        <td>
                            <input type="text" v-model="name">
                        </td>
                        <th id="th_condition">Address</th>
                        <td><input type="text" v-model="address"></td>
                    </tr>
                    <tr>
                        <th id="th_condition">Tel</th>
                        <td>
                            <input type="text" v-model="tel">
                        </td>
                        <th id="th_condition">Country</th>
                        <td width="50px" colspan="2">
                            <select v-model="countryCode" id="selectedCountryCode">
                                <option value=""> </option>
                                <option v-for="item in countryList" v-bind:key="item.name" v-bind:value="item.code">
                                    {{ item.name }}
                                </option>
                            </select>
                        </td>
                    </tr>
                </table>
                <div class="button_align">
                    <button @click="search" type="button" class="btn btn-primary">Search</button>
                </div>
            </form>
        </dev>    
        <br>
        <br>
        <!-- Message -->
        <div v-show="message" class="alert alert-success">{{ message }}</div>
        <div v-show="errMessage" class="alert alert-danger">{{errMessage}}</div>
        <!-- user List -->
        <table class="table">
            <thead>
                <tr>
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <th scope="col">Address</th>
                <th scope="col">Tel</th>
                <th scope="col">Country</th>
                <th scope="col">&nbsp;</th>
                <th scope="col">&nbsp;</th>
                </tr>
            </thead>
            <tbody>   
                <tr v-for="user in users" :key="user.id">
                    <th scope="row">{{user.id}}</th>
                    <td>{{user.name}}</td>
                    <td>{{user.address}}</td>   
                    <td>{{user.tel}}</td>
                    <td>{{user.country.name}}</td>
                    <td>
                        <a href="#" @click.stop.prevent="() => $router.push({ name: 'Edit', params: { id: user.id } })"  >
                            <span class="material-symbols-outlined">edit</span>
                        </a>
                    </td>
                    <td> 
                        <a href="#" @click.stop.prevent="deleteUser(user.id, user.name)" >
                            <span class="material-symbols-outlined">delete</span>
                        </a>
                    </td>
                </tr>   
            </tbody>
        </table>
        <!-- <span>
            <button @click="()=>$router.push({ name: 'Add'})" type="button" class="btn btn-primary"> User Add </button>
        </span> -->
    </div>
</template>
<script>
    import axios from 'axios';
    export default {
        data(){
            return{
                users: [],
                message: "",
                errMessage: "",
                countryList: [],
                // conditions
                consId :"",
                countryCode: "",
                name: "",
                address: "",
                tel: ""
            }
        },
        mounted(){
            axios.get('http://localhost:8080/init')
                .then (
                    response => {this.countryList = response.data;} 
                ).catch (
                    error => {
                        this.errMessage = error.message
                        switch (error.response?.status) {
                            case 401:
                                alert(`message 401: ${error}`);
                            case 403:
                                alert(`message 404: ${error}`);
                            default:
                                alert(`message default: ${error}`);
                        }
                    }
                ); 
        },
        methods: {
            search (){
                this.message = "";
                axios
                .get('http://localhost:8080/search', { 
                    params: {
                        id : this.consId,
                        name : this.name,
                        address : this.address,
                        tel : this.tel,
                        countryCode : this.countryCode
                    }
                }).then(
                    response => { 
                        if (response.data.length === 0) {
                            this.message = "User Not Found"
                        }                 
                       this.users = response.data; 
                    } 
                ).catch(
                    error => {
                        this.errMessage ="Failed to get user list";
                    }
                );
            },
            deleteUser(paramId, paramName) {
                axios
                .delete(`http://localhost:8080/delete/${paramId}`)
                .then(
                    response => {
                        this.search();
                        this.message = "User " + paramName + " has been deleted. ";
                    } 
                ).catch(
                    error => {
                            this.errMessage ="Failed to delete user";
                    }    
                );
            }
        }
    }
</script>
<style>

.condition {
    display: inline-block;
    width: 100%;
    border: 0.5px solid #b2b0b0;
    background: #ebeff1;
    padding: 10px;
    box-sizing: border-box;
}

.condition table {
    width: 400px;
    /* border: 0.5px solid #b2b0b0; */
}
.condition th {
    width: 15%;
    text-align: center;
}


</style>
