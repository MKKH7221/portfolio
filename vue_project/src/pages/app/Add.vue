<template>
  <div class="content">
      <h3>Create User</h3>
      <div class="create" style="float: left;">
        <div v-show="errMessage" class="alert alert-danger">{{errMessage}}</div>
        <div v-show="message" class="alert alert-success">{{ message }}</div>
        <form v-on:submit.prevent="addUser">

          <!-- name -->
          <div class="form-group" :class="{ error: v$.form.name.$errors.length }">
              <label for="">Name</label>
              <input class="form-control" placeholder="name" type="name" v-model="v$.form.name.value.$model">
              <div class="input-errors" v-for="(error, index) of v$.form.name.$errors" :key="index">
                  <div class="error-msg">{{ error.$message }}</div>
              </div>
          </div>
          <br>
          <!-- address -->
          <div class="form-group" :class="{ error: v$.form.address.$errors.length }">
              <label for="">Address</label>
              <input class="form-control" placeholder="address" type="address" v-model="v$.form.address.value.$model">
              <div class="input-errors" v-for="(error, index) of v$.form.address.$errors" :key="index">
                  <div class="error-msg">{{ error.$message }}</div>
              </div>
          </div>
          <br>
          <!-- tel -->
          <div class="form-group" :class="{ error: v$.form.tel.$errors.length }">
              <label for="">Tel</label>
              <input class="form-control" placeholder="tel" type="tel" v-model="v$.form.tel.value.$model">
              <div class="input-errors" v-for="(error, index) of v$.form.tel.$errors" :key="index">
                  <div class="error-msg">{{ error.$message }}</div>
              </div>
          </div>
          <br>
          <!-- country -->
          <div class="form-group" :class="{ error: v$.form.country.code.$errors.length }">
              <label for="">Country</label>
              <select class="form-control" type="countryCode" v-model="v$.form.country.code.$model">
                  <option value="" placeholder="select a country">select a country</option>
                  <option v-for="country in countryList" 
                      v-bind:key="country.code" v-bind:value="country.code">
                      {{ country.name }}
                  </option>
              </select>
              <div class="input-errors" v-for="(error, index) of v$.form.country.$errors" :key="index">
                  <div class="error-msg">{{ error.$message }}</div>
              </div>
          </div>
          <br>                    
          <p class="button_align">
              <button type="submit" class="btn btn-primary" :disabled="v$.$invalid">Create</button>
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
    name: 'addUser',
    setup () {
        return { v$:useVuelidate() }
    },
    data(){
        return{
          form: {
            name: { 
                value: ""
            },
            address: { 
              value: "" 
            } ,
            tel: { 
              value: "" 
            } ,
            country: {
                name: "",
                code: ""
            }
          },   
          errMessage : "",
          message : "",
          countryList: []
        }
    },
    validations() {
        return {
            form: {
                name: {
                  value: {
                    required,
                    maxLength: maxLength(20),
                    alphaSpace: helpers.withMessage('Name must be used alphabet or space', alphaSpace),
                  }
                },
                address: {
                  value: {
                    required,
                    maxLength: maxLength(100),
                    alphaSpaceNumComma: helpers.withMessage('Address must be used alphabet, number, space, comma, hyphen, slash', alphaSpaceNumComma),
                  }  
                },
                tel: {
                  value: {
                    required,
                    numeric,
                    maxLength: maxLength(10),
                  }
                },
                country: {
                    code: {
                      required
                    }
                }
            },
        }
    },
    created() {
        this.init();
        if (this.$v == null ) {
          console.log("Validation is null");
        }
    },
    methods: {
        addUser() {
          axios
          .post("http://localhost:8080/add", this.form)
          .then(
            response => {
              this.form.name.value="";
              this.form.address.value="";
              this.form.tel.value="";
              this.form.country.name="";
              this.form.country.code="";
              this.v$.form.$reset();
              this.message = "The user have successfully created";
            } 
          ).catch(
            error => {
              this.message = `Failed to create user`;
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
                    this.errMessage = `Faild to get countryList`;
                }
            ); 
        }
    }
    
  }
  </script>
  
  <style>
  .create {
    display: block;
    width: 60%;
    border: 0.5px solid #b2b0b0;
    /* background: #ebeff1; */
    margin: 20px auto;
    padding: 20px;
    box-sizing: border-box;
}
</style>
  