<template>
    <div class="content" >
        <h3>Edit User </h3>    
        <div class="edit" style="float: left;">
            <div v-show="errMessage" class="alert alert-danger">{{errMessage}}</div>
            <div v-show="message" class="alert alert-success">{{ message }}</div>
            <form @submit.prevent="this.update()">
                    <!-- id -->
                    <div class="form-group">
                        <label class="label" for="id">Id</label>
                        <input class="form-control" type="id" v-model="form.id.value" disabled="true"/>
                    </div>
                    <br>
                    <!-- name -->
                    <div class="form-group" :class="{ error: v$.form.name.$errors.length }">
                        <label for="">Name</label>
                        <input class="form-control" type="name" v-model="v$.form.name.value.$model">
                        <!-- <div class="pre-icon os-icon os-icon-user-male-circle"></div> -->
                        <div class="input-errors" v-for="(error, index) of v$.form.name.$errors" :key="index">
                            <div class="error-msg">{{ error.$message }}</div>
                        </div>
                    </div>
                    <br>
                    <!-- address -->
                    <div class="form-group" :class="{ error: v$.form.address.$errors.length }">
                        <label for="">Address</label>
                        <input class="form-control" type="address" v-model="v$.form.address.value.$model">
                        <div class="input-errors" v-for="(error, index) of v$.form.address.$errors" :key="index">
                            <div class="error-msg">{{ error.$message }}</div>
                        </div>
                    </div>
                    <br>
                    <!-- tel -->
                    <div class="form-group" :class="{ error: v$.form.tel.$errors.length }">
                        <label for="">Tel</label>
                        <input class="form-control"  type="tel" v-model="v$.form.tel.value.$model">
                        <!-- error message -->
                        <div class="input-errors" v-for="(error, index) of v$.form.tel.$errors" :key="index">
                            <div class="error-msg">{{ error.$message }}</div>
                        </div>
                    </div>
                    <br>
                    <!-- country -->
                    <div class="form-group" :class="{ error: v$.form.country.code.$errors.length }">
                        <label for="">Country</label>
                        <select class="form-control" type="country.code" v-model="v$.form.country.code.$model">
                            <option value="" > Select a country </option>
                            <option v-for="country in countryList" 
                                v-bind:key="country.code" v-bind:value="country.code">
                                {{ country.name }}
                            </option>
                        </select>
                        <!-- error message -->
                        <div class="input-errors" v-for="(error, index) of v$.form.country.code.$errors" :key="index">
                            <div class="error-msg">{{ error.$message }}</div>
                        </div>
                    </div>
                    <br>                    
                    <p class="button_align">
                        <button type="submit" class="btn btn-primary" :disabled="v$.$invalid">Confirm</button>
                    </p>
            </form>
        </div>
        <div class="backto">
            <p><router-link to="/search">Go back to Search</router-link></p>
        </div>
    </div>
</template>



<script>
  import { ref } from 'vue';
  import axios from "axios";
  import useVuelidate from '@vuelidate/core'
  import { required, numeric, maxLength, helpers} from '@vuelidate/validators'
  const alphaSpace = helpers.regex(/^[a-zA-Z ]*$/)
  const alphaSpaceNumComma = helpers.regex(/^[a-zA-Z0-9 ,-\/]*$/)
 export default {
    name: 'editUser',    
    setup () {
        return { v$:useVuelidate() }
    },
    data(){
        return{
            form: {
                id: { value : null } ,
                name: { value : null } ,
                tel: { value : null } ,
                address: { value : null } ,
                country : {
                    name: null,
                    code: null
                }
            },
            id : "",
            countryList: [], 
            message : "",
            errMessage :""
        }
    },
    validations() {
        return {
            form: {
                name: {
                    value:{
                        required,
                        maxLength: maxLength(20),
                        alphaSpace: helpers.withMessage('Name must be used alphabet or space', alphaSpace),
                    }
                },
                address: {
                    value:{
                        required,
                        maxLength: maxLength(100),
                        alphaSpaceNumComma: helpers.withMessage('Address must be used alphabet, number, space, comma, hyphen, slash', alphaSpaceNumComma),
                    }
                },
                tel: {
                    value:{
                        required,
                        numeric,
                        maxLength: maxLength(10),
                    }
                },
                country: {
                    code:{
                        required
                    }
                }
            },
        }
    },
   created() {
        this.id = this.$route.params.id;
        this.getResults();
        this.init();
    },
    methods: {
        getResults() {
            console.log(this.id);
            axios
            .get('http://localhost:8080/edit/'+this.id)
            .then(
                response => {this.form = response.data;} 
            ).catch(
                error => { 
                    if (error.response.status) {
                        this.errMessage = `error: status: ${error.response.status}, message: ${error.response.data.$message}`;
                    } else {
                        this.errMessage = "getResults : Failed to find user id ["+ this.id+"]"; 
                    }
                }
            );    
        },
        update() {
            // alert(JSON.stringify(this.form));
            this.form.country.name = "";
            axios
            .put('http://localhost:8080/update', this.form)
            .then(
                response => {
                    this.form = response.data;
                    this.message="The update has been completed";
                } 
            ).catch(
                error => {
                    if (error.response.status) {
                        this.errMessage = `error: status: ${error.response.status}, message: ${error.response.data.$message}`;
                    } else {
                        this.errMessage = `Fail to edit user`;
                    }
                }
            );    
        },
        init () {
            axios.get('http://localhost:8080/init')
            .then (
                response => {
                    this.countryList = response.data;
                } 
            ).catch (
                error => {
                    if (error.response.status) {
                        this.errMessage = `init:status: ${error.response.status}, message: ${error.response.data}`;
                    } else {
                        this.errMessage = `Fail to get country List`;
                    }
                }
            ); 
        },
    }
  }
  </script>

<style>

.edit {
    display: block;
    width: 60%;
    border: 0.5px solid #b2b0b0;
    /* background: #ebeff1; */
    margin: 20px auto;
    padding: 20px;
    box-sizing: border-box;
}
.backto {
    display: block;
    width: 60%;
}

</style>
