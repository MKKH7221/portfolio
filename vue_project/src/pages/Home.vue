
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
                      <b>URI Parameters</b><br>
                      <ul>
                          <li>id: number &nbsp;&nbsp;  Example: 1</li>
                          <li>name: string &nbsp;&nbsp; Example: "Galireo"</li>
                          <li>address: string &nbsp;&nbsp; Example: "Firenze"</li>
                          <li>tel: string  &nbsp;&nbsp; Example: "345"</li>
                          <li>countryCode: string &nbsp;&nbsp; Example: "ITA"</li>
                      </ul>
                  </td>
                  <td>/search/?id=1&name="Galireo"</td>
                </tr>  
                <tr>
                  <td>Search User</td>
                  <td>Get</td>
                  <td>/edit/{id} </td>
                  <td>
                      <b>URI Parameters</b><br>
                      <ul>
                        <li>id: number (required) &nbsp;&nbsp; Example: 1</li>
                      </ul>
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
                      <ul>
                        <li>id: number (required) &nbsp;&nbsp; Example: 1 </li>
                        <li>name: string (required) &nbsp;&nbsp; Example: "Galireo"</li>
                        <li>address: string (required) &nbsp;&nbsp; Example: "Firenze"</li>
                        <li>tel: string (required) &nbsp;&nbsp; Example: "345"</li>
                        <li>country.code: string (required) &nbsp;&nbsp; Example: "ITA"</li>
                      </ul>
                    </p>    
                  </td>
                  <td> 
                    <span>
                      {<br>
                        &nbsp;&nbsp; id: { <br>
                        &nbsp;&nbsp;&nbsp;&nbsp; value : 15 <br>
                        &nbsp;&nbsp;} ,<br>
                        &nbsp;&nbsp;name: { <br>
                        &nbsp;&nbsp;&nbsp;&nbsp; value : "Galileo Galilei" <br>
                        &nbsp;&nbsp;} ,<br>
                        &nbsp;&nbsp; address: { <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;value : "Piazza Navona, 00186 Roma" <br>
                        &nbsp;&nbsp;} ,<br>
                        &nbsp;&nbsp; tel: { <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;  value : "2324232432" <br>
                        &nbsp;&nbsp;} ,<br>
                        &nbsp;&nbsp; country : {<br>
                        &nbsp;&nbsp;&nbsp;&nbsp; name: "",<br>
                        &nbsp;&nbsp;&nbsp;&nbsp; code: "ITA"<br>
                        &nbsp;&nbsp; }<br>
                      }
                    </span>    
                  </td>
                </tr>  
                <tr>  
                  <td>Create User</td>
                  <td>Post</td>
                  <td>/add</td>
                  <td> 
                      <b>With Body</b><br>
                      <ul>
                        <li>name: string (required) &nbsp;&nbsp; Example: "Galireo"</li>
                        <li>address: string (required) &nbsp;&nbsp; Example: "Firenze"</li>
                        <li>tel: string (required) &nbsp;&nbsp; Example: "345"</li>
                        <li>country.code: string (required) &nbsp;&nbsp; Example: "ITA"</li>
                      </ul>
                  </td>
                  <td> 
                      {<br>
                        &nbsp;&nbsp; name: { <br>
                        &nbsp;&nbsp;&nbsp;&nbsp; value : "Galileo Galilei"<br>
                        &nbsp;&nbsp;} ,<br>
                        &nbsp;&nbsp; address: { <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;  value : "Piazza Navona, 00186 Roma" <br>
                        &nbsp;&nbsp;} ,<br>
                        &nbsp;&nbsp; tel: { <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;value : "2324232432" <br>
                        &nbsp;&nbsp;} ,<br>
                        &nbsp;&nbsp; country : {<br>
                        &nbsp;&nbsp;&nbsp;&nbsp; name: "",<br>
                        &nbsp;&nbsp;&nbsp;&nbsp; code: "ITA"<br>
                        &nbsp;&nbsp; }<br>
                    }<br>
                  </td>
                </tr>  
                <tr>  
                  <td>Delete user</td>
                  <td>Delete</td>
                  <td>/delete</td>
                  <td> 
                      <b>With Body</b>
                      <ul><li>value: integer (required) &nbsp;&nbsp; Exsample: 1</li></ul>
                  </td>
                  <td> 
                      <b>With Body </b><br>
                      { value : 1 } 
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
