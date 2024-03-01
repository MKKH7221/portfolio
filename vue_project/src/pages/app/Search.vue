<template>
    <div class="content">
        <dev class="condition" >
            <h4> Criteria</h4>
            <form>
                <table>
                    <tr>
                        <th>ID</th>
                        <td>
                            <div class="form-group" :class="{ error: v$.form.consId.$errors.length }">
                                <input class="form-control txt" type="text" v-model="v$.form.consId.$model">
                                <div class="input-errors" v-for="(error, index) of v$.form.consId.$errors" :key="index">
                                    <div class="error-msg">{{ error.$message }}</div>
                                </div>
                            </div>
                        </td>
                        <th>Name</th>
                        <td>
                            <div class="form-group" :class="{ error: v$.form.name.$errors.length }">
                                <input class="form-control" type="text" v-model="v$.form.name.$model">
                                <div class="input-errors" v-for="(error, index) of v$.form.name.$errors" :key="index">
                                    <div class="error-msg">{{ error.$message }}</div>
                                </div>
                            </div>
                        </td>
                        <th>Address</th>
                        <td>
                            <div class="form-group" :class="{ error: v$.form.address.$errors.length }">
                                <input class="form-control" type="text" v-model="v$.form.address.$model">
                                <div class="input-errors" v-for="(error, index) of v$.form.address.$errors" :key="index">
                                    <div class="error-msg">{{ error.$message }}</div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>Tel</th>
                        <td>
                            <div class="form-group" :class="{ error: v$.form.tel.$errors.length }">
                                <input class="form-control" type="text" v-model="v$.form.tel.$model">
                                <div class="input-errors" v-for="(error, index) of v$.form.tel.$errors" :key="index">
                                    <div class="error-msg">{{ error.$message }}</div>
                                </div>
                            </div>
                        </td>
                        <th>Country</th>
                        <td width="50px" colspan="2">
                            <select class="form-control" v-model="form.countryCode" id="selectedCountryCode">
                                <option value=""> </option>
                                <option v-for="item in countryList" v-bind:key="item.name" v-bind:value="item.code">
                                    {{ item.name }}
                                </option>
                            </select>
                        </td>
                    </tr>
                </table>
                <div class="button_align">
                    <button @click="search" type="button" class="btn btn-primary" :disabled="v$.$invalid">Search</button>
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
                <tr v-for="user in users" :key="user.id.value">
                    <th scope="row">{{user.id.value}}</th>
                    <td>{{user.name.value}}</td>
                    <td>{{user.address.value}}</td>   
                    <td>{{user.tel.value}}</td>
                    <td>{{user.country.name}}</td>
                    <td>
                        <a href="#" @click.stop.prevent="() => $router.push({ name: 'Edit', params: { id : user.id.value } })"  >
                            <span class="material-symbols-outlined">edit</span>
                        </a>
                    </td>
                    <td> 
                        <a href="#" @click.stop.prevent="deleteUser(user.id.value, user.name.value)" >
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
    import useVuelidate from '@vuelidate/core'
    import { numeric, maxLength, helpers} from '@vuelidate/validators'
    const alphaSpace = helpers.regex(/^[a-zA-Z ]*$/)
    const alphaSpaceNumComma = helpers.regex(/^[a-zA-Z0-9 ,-\/]*$/)

    export default {
        setup () {
            return { v$:useVuelidate() }
        },
        data(){
            return{
                users: [],
                message: "",
                errMessage: "",
                countryList: [],
                // conditions
                form: {
                    consId :"",
                    countryCode: "",
                    name: "",
                    address: "",
                    tel: ""
                }
            }
        },
        validations() {
            return {
                form: {
                    consId: {
                        numeric,
                    },
                    name: {
                        alphaSpace: helpers.withMessage('Name must be used alphabet or space', alphaSpace),
                    },
                    address: {
                        alphaSpaceNumComma: helpers.withMessage('Address must be used alphabet, number, space, comma, hyphen, slash', alphaSpaceNumComma),
                    },
                    tel: {
                        numeric,
                        maxLength: maxLength(10),
                    },
                },
        }
    },
    mounted(){
        axios.get('http://localhost:8080/init')
            .then (
                response => {this.countryList = response.data;} 
            ).catch (
                error => {
                    this.errMessage = error.message
                    switch (error.response.status) {
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
                        id : this.form.consId,
                        name : this.form.name,
                        address : this.form.address,
                        tel : this.form.tel,
                        countryCode : this.form.countryCode
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
                .delete(`http://localhost:8080/delete`, { data:{value: paramId }} )
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
    padding: 20px;
    box-sizing: border-box;
}

.condition table {
    width: 80%;
    /* border: 0.5px solid #b2b0b0; */
}
.condition th {
    width: 30em;
    text-align: center;
}
.condition td {
    width: 80em;
    text-align: center;
}

</style>
