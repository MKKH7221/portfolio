
import { required } from '@vuelidate/validators';
<template>
      <div class="content">        
            <h3>API Specification</h3>
              Basic API Specifications for User Management Application API<br>
              <table>
                <tr>
                  <th>Description</th>
                  <th>Http Method</th>
                  <th>Path</th>
                  <th>Request</th>
                  <th>Example</th>
                </tr>  
                <tr>
                  <td>
                    Search User<br>
                    With Criteria
                  </td>
                  <td>Get</td>
                  <td>/search</td>
                  <td>
                    <p >
                      <b>URI Parameters</b><br>
                        id: number Example: 1 <br> 
                        name: string Example: "Galireo"<br>
                        address: string Example: "Firenze"<br>
                        tel: string Example: "345"<br>
                        countryCode: string Example: "ITA"<br>
                    </p>
                  </td>
                  <td>/search/?id=1&name="Galireo"</td>
                </tr>  
                <tr>
                  <td>Search User</td>
                  <td>Get</td>
                  <td>/edit/{id} </td>
                  <td>
                    <span>
                      <b>With Body </b><br>
                      id: number required Example: 1<br>
                    </span>
                  </td>
                  <td>/edit/1</td>
                </tr>  
                <tr>
                  <td>Update User</td>
                  <td>Patch</td>
                  <td>/update </td>
                  <td> 
                    <p>
                      <b>With Body </b><br>
                        id: number required Example: 1 <br>
                        name: string required Example: "Galireo"<br>
                        address: string required Example: "Firenze"<br>
                        tel: string required Example: "345"<br>
                        country.code: string required Example: "ITA"<br>
                    </p>    
                  </td>
                  <td> 
                    <span>
                      {<br>
                        &nbsp&nbsp id: { value : 15 } ,<br>
                        &nbsp&nbsp name: { value : "Galileo Galilei" } ,<br>
                        &nbsp&nbsp address: { value : "Piazza Navona, 00186 Roma" } ,<br>
                        &nbsp&nbsp tel: { value : "2324232432" } ,<br>
                        &nbsp&nbsp country : {<br>
                        &nbsp&nbsp&nbsp&nbsp name: "",<br>
                        &nbsp&nbsp&nbsp&nbsp code: "ITA"<br>
                        &nbsp&nbsp }<br>
                      }<br>
                    </span>    
                  </td>


                </tr>  
                <tr>  
                  <td>Create User</td>
                  <td>Post</td>
                  <td>/add</td>
                  <td> 
                      <b>With Body</b><br>
                        name: string required Example: "Galireo"<br>
                        address: string required Example: "Firenze"<br>
                        tel: string required Example: "345"<br>
                        country.code: string required Example: "ITA"<br>
                  </td>
                  <td> 
                      {<br>
                        &nbsp&nbsp name: { value : "Galileo Galilei" } ,<br>
                        &nbsp&nbsp address: { value : "Piazza Navona, 00186 Roma" } ,<br>
                        &nbsp&nbsp tel: { value : "2324232432" } ,<br>
                        &nbsp&nbsp country : {<br>
                        &nbsp&nbsp&nbsp&nbsp name: "",<br>
                        &nbsp&nbsp&nbsp&nbsp code: "ITA"<br>
                        &nbsp&nbsp }<br>
                    }<br>
                  </td>
                </tr>  
                <tr>  
                  <td>Delete user</td>
                  <td>Delete</td>
                  <td>/delete</td>
                  <td> 
                    <p>
                      <b>With Body </b><br>
                        value: integer required Exsample: 1<br>
                    </p>    
                  </td>
                  <td> 
                    <p>
                      <b>With Body </b><br>
                        { value : 1 } <br>
                    </p>    
                  </td>
                </tr>  
                </table>  
  </div>
</template>

<style scoped>
table {
  border: 1px solid gray;
  width: 100%;
  border-spacing: 10px;
}
th {
  padding: 0.3em;
  border: 1px solid gray;
  vertical-align:top;
}
td {
    padding: 0.3em;
    border: 1px solid gray;
    vertical-align:top;
}
</style>
