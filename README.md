# Flavor Network
## Introduction
The code in this repository **reproduces** the results presented in the paper *"Flavor network and the principles of food pairing"* by **Yong-Yeol Ahn**, **Sebastian E. Ahnert**, **James P. Bagrow** & **Albert-László Barabási** [1]. The paper analyzes the **ingredients** used in **recipes** from various **cuisines** in order to determine which ingredients **mix well** together to form recipes, as well as find the **authentic ingredients** for the analyzed cuisines. The results show that western cuisines (such as North American and Northern European) tend to mix ingredients that **share** flavor compounds, while eastern cuisines (for example Asian cuisines) are more biased towards ingredients that **do not share** flavor compounds.

The code for the simulations is implemented in **Java** and is located in the **src folder**. After processing the data, the results are visualized using MatLab.

## Initial ingredient analysis
To start with, the authors do some basic analysis of the **ingredients** in their data set, such as the **probability distribution** of the **number of ingredients** across all cousines and the **frequency of appearence** of ingredients in each cousine:

<p align="center">
<img src="https://github.com/Pepton21/flavor-network/blob/master/images/Fig12.PNG" width="650" alt="F12">
</p>

## Definition of measures

In continuation, the **mean number of shared compounds** in a recipe (**N<sub>S</sub>**) is defined as the average number of
**shared compounds** by all ingredients within a recipe and is calculated by:

<p align="center">
<a href="https://www.codecogs.com/eqnedit.php?latex=N_S(R)&space;=&space;\frac{1}{n_R(n_R&space;-&space;1)}&space;\sum_{i,j\in&space;R,&space;i\neq&space;j}|C_i\cap&space;C_j|" target="_blank"><img src="https://latex.codecogs.com/gif.latex?N_S(R)&space;=&space;\frac{1}{n_R(n_R&space;-&space;1)}&space;\sum_{i,j\in&space;R,&space;i\neq&space;j}|C_i\cap&space;C_j|" title="N_S(R) = \frac{1}{n_R(n_R - 1)} \sum_{i,j\in R, i\neq j}|C_i\cap C_j|" /></a>
</p>

If the mean number of shared components in a cuisine is higher, that means that recipes within that cuisine contain more ingredients that **share** flavor compounds. If we calculate this measures for all cuisines, we can see that western cuisines favor recipes that **share** flavor compounds, while Asian cuisines favor recipes that have **diverse** flavors:

<p align="center">
<img src="https://github.com/Pepton21/flavor-network/blob/master/images/Fig3.PNG" width="950" alt="F3">
</p>

To prove that western cuisines mix compound sharing ingredients in above average amounts and that the opposite holds for eastern cuisines, we can analyze the **∆N<sub>S</sub>** measure defined as the **difference** between the mean number of shared compounds in a **given cuisine** and the mean number of shared compounds in a **randomly generated cuisine**:

<p align="center">
<a href="https://www.codecogs.com/eqnedit.php?latex=\Delta&space;N_S(C)&space;=&space;N_S(C)&space;-&space;N_S(C^{rand})" target="_blank"><img src="https://latex.codecogs.com/gif.latex?\Delta&space;N_S(C)&space;=&space;N_S(C)&space;-&space;N_S(C^{rand})" title="\Delta N_S(C) = N_S(C) - N_S(C^{rand})" /></a>
</p>

We can generate a **random cuisine** using a **Poisson distribution** to obtain the number of ingredients in each recipe (**λ** is the average number of ingredients per recipe), and then a **Uniform distribution** to add **random ingredients** to the recipes. However, we obtained better results by generating the random cuisine by randomly adding 10000 **existing recipes** (first uniformly choose a random existing cuisine, then uniformly choose a random recipe within that cuisine). If we subtract the **N<sub>S</sub>** value of the random cuisine from the **N<sub>S</sub>** values of each cuisine, we obtain **positive** values for the **western cuisines** and **negative** values for the **eastern cuisines**:

<p align="center">
<img src="https://github.com/Pepton21/flavor-network/blob/master/images/Fig4.PNG" width="1150" alt="F4">
</p>


This **confirms** that western cuisines mix more compound sharing ingredients than the random (average) cuisine, whereas eastern cuisines mix less compound sharing ingredients. If we define a function that takes any possible **number of shared flavor compounds** between **two ingredients** as an argument and returns the **fraction of ingredient pairs** in the cuisine that share that many compounds and are part of a recipe, we obtain the following graphs:

<p align="center">
<img src="https://github.com/Pepton21/flavor-network/blob/master/images/Fig5.PNG" width="1050" alt="F5">
</p>

It can be seen that as the number of shared components between ingredient pairs rises, the fraction of pairs that keep up with this increment **rises** for the **North American** cuisine, but **decreases** for the **East Asian** cuisine.

## Ingredient contribution analysis
The next step is analyzing which ingredients **contribute** to the **N<sub>S</sub>** measure the most in each cuisine. The contribution of an ingredient  (**χ<sub>i</sub>**) is calculated as:

<p align="center">
<a href="https://www.codecogs.com/eqnedit.php?latex=\chi&space;_i&space;=&space;N_S(C)&space;-&space;N_S(C^{i'})" target="_blank"><img src="https://latex.codecogs.com/gif.latex?\chi&space;_i&space;=&space;N_S(C)&space;-&space;N_S(C^{i'})" title="\chi _i = N_S(C) - N_S(C^{i'})" /></a>,
</p>

<i>where C<sup>i<sub>0</sub></sup> represents the cuisine C without the ingredient i.</i>

From the definition of the contribution measure we can see that if the contribution of the ingredient is **positive**, the removal of that ingredient from the cuisine would cause the **N<sub>S</sub>** measure to **decrease**. On the other hand, if the contribution of the ingredient is **negative**, the removal of the ingredient would cause the **N<sub>S</sub>** measure to **increase**. In other words, the **higher the contribution** of an ingredient, the **more flavor compounds** it **shares** with other ingredients within the cuisine.

To confirm that removing positive/negative contributions decreases/increases the **N<sub>S</sub>** measure, we can start removing the **most contributing** ingredients from the North American cuisine and the **least contributing** ingredients from the East Asian cuisine one by one. The results are shown below:

<p align="center">
<img src="https://github.com/Pepton21/flavor-network/blob/master/images/Fig6.PNG" width="1050" alt="F6">
</p>

## Cuisine authenticity

Finally, the authors discover the most authentic ingredients, ingredient pairs and ingredient triplets in different cuisines. To do this, the following measures are defined:

<i>***Definition:*** The **prevalence** (**P<sub>i</sub><sup>C</sup>**) is the **fraction of recipes** in **cuisine C** that contain the **ingredient i**:</i>

<p align="center">
<a href="https://www.codecogs.com/eqnedit.php?latex=P_i^C&space;=&space;n_i^C&space;/&space;N_C" target="_blank"><img src="https://latex.codecogs.com/gif.latex?P_i^C&space;=&space;n_i^C&space;/&space;N_C" title="P_i^C = n_i^C / N_C" /></a>
</p>

<i>where n<sub>i</sub><sup>C</sup> is the number of recipes in cuisine C containing ingredient i.</i>

<i>***Definition:*** The **relative prevalence** (**p<sub>i</sub><sup>C</sup>**) measures the **authenticity** of the **ingredient i** with respect to the **cuisine C** and is calculated by:</i>

<p align="center">
<a href="https://www.codecogs.com/eqnedit.php?latex=p_i^C&space;=&space;P_i^C&space;-&space;\langle&space;P_i^{C'}&space;\rangle&space;_{C'\neq&space;C}" target="_blank"><img src="https://latex.codecogs.com/gif.latex?p_i^C&space;=&space;P_i^C&space;-&space;\langle&space;P_i^{C'}&space;\rangle&space;_{C'\neq&space;C}" title="p_i^C = P_i^C - \langle P_i^{C'} \rangle _{C'\neq C}" /></a>
</p>

<i>where <P<sub>i</sub><sup>C'</sup>><sub>C'≠C</sub> is the average prevalence of ingredient i in all cuisines other than C.</i>

These definitions can be extended to **ingredient pairs** and **triplets**:

<p align="center">
<a href="https://www.codecogs.com/eqnedit.php?latex=p_{ij}^C&space;=&space;P_{ij}^C&space;-&space;\langle&space;P_{ij}^{C'}&space;\rangle&space;_{C'\neq&space;C},&space;\;&space;P_{ij}^C&space;=&space;n_{ij}^C&space;/&space;N_C" target="_blank"><img src="https://latex.codecogs.com/gif.latex?p_{ij}^C&space;=&space;P_{ij}^C&space;-&space;\langle&space;P_{ij}^{C'}&space;\rangle&space;_{C'\neq&space;C},&space;\;&space;P_{ij}^C&space;=&space;n_{ij}^C&space;/&space;N_C" title="p_{ij}^C = P_{ij}^C - \langle P_{ij}^{C'} \rangle _{C'\neq C}, \; P_{ij}^C = n_{ij}^C / N_C" /></a>
</p>
 
<p align="center">
 <a href="https://www.codecogs.com/eqnedit.php?latex=p_{ijk}^C&space;=&space;P_{ijk}^C&space;-&space;\langle&space;P_{ijk}^{C'}&space;\rangle&space;_{C'\neq&space;C},&space;\;&space;P_{ijk}^C&space;=&space;n_{ijk}^C&space;/&space;N_C" target="_blank"><img src="https://latex.codecogs.com/gif.latex?p_{ijk}^C&space;=&space;P_{ijk}^C&space;-&space;\langle&space;P_{ijk}^{C'}&space;\rangle&space;_{C'\neq&space;C},&space;\;&space;P_{ijk}^C&space;=&space;n_{ijk}^C&space;/&space;N_C" title="p_{ijk}^C = P_{ijk}^C - \langle P_{ijk}^{C'} \rangle _{C'\neq C}, \; P_{ijk}^C = n_{ijk}^C / N_C" /></a>
</p>

Using these definitions, the following authentic ingredients are obtained:

<table>
  <thead>
    <tr>
      <th colspan=6>
        North American
      </th>
    </tr>
    <tr>
      <th>Ingredient</th>
      <th>Prev.</th>
      <th>Pair</th>
      <th>Prev.</th>
      <th>Triplet</th>
      <th>Prev.</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>butter</td>
      <td>0.4115</td>
      <td>egg, wheat</td>
      <td>0.2758</td>
      <td>butter, egg, wheat</td>
      <td>0.1917</td>
    </tr>
    <tr>
      <td>egg</td>
      <td>0.4030</td>
      <td>butter, wheat</td>
      <td>0.2634</td>
      <td>egg, wheat, milk</td>
      <td>0.1356</td>
    </tr>
    <tr>
      <td>wheat</td>
      <td>0.3981</td>
      <td>butter, egg</td>
      <td>0.2240</td>
      <td>egg, wheat, vanilla</td>
      <td>0.1307</td>
    </tr>
    <tr>
      <td>onion</td>
      <td>0.2964</td>
      <td>wheat, milk</td>
      <td>0.1771</td>
      <td>butter, wheat, milk</td>
      <td>0.1254</td>
    </tr>
    <tr>
      <td>milk</td>
      <td>0.2648</td>
      <td>egg, milk</td>
      <td>0.1672</td>
      <td>butter, wheat, vanilla</td>
      <td>0.1149</td>
    </tr>
    <tr>
      <td>garlic</td>
      <td>0.2432</td>
      <td>butter, milk</td>
      <td>0.1594</td>
      <td>butter, egg, vanilla</td>
      <td>0.1097</td>
    </tr>
  </tbody>
</table>

<table>
  <thead>
    <tr>
      <th colspan=6>
        Western European
      </th>
    </tr>
    <tr>
      <th>Ingredient</th>
      <th>Prev.</th>
      <th>Pair</th>
      <th>Prev.</th>
      <th>Triplet</th>
      <th>Prev.</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>butter</td>
      <td>0.5236</td>
      <td>butter, wheat</td>
      <td>0.3555</td>
      <td>butter, egg, wheat</td>
      <td>0.2655</td>
    </tr>
    <tr>
      <td>egg</td>
      <td>0.4890</td>
      <td>egg, wheat</td>
      <td>0.3498</td>
      <td>egg, wheat, milk</td>
      <td>0.1547</td>
    </tr>
    <tr>
      <td>wheat</td>
      <td>0.4795</td>
      <td>butter, egg</td>
      <td>0.3067</td>
      <td>butter, wheat, milk</td>
      <td>0.1446</td>
    </tr>
    <tr>
      <td>onion</td>
      <td>0.2694</td>
      <td>egg, milk</td>
      <td>0.1911</td>
      <td>butter, egg, milk</td>
      <td>0.1299</td>
    </tr>
    <tr>
      <td>milk</td>
      <td>0.2601</td>
      <td>wheat, milk</td>
      <td>0.1889</td>
      <td>egg, wheat, cream</td>
      <td>0.0999</td>
    </tr>
    <tr>
      <td>cream</td>
      <td>0.2402</td>
      <td>butter, milk</td>
      <td>0.1738</td>
      <td>butter, wheat, cream</td>
      <td>0.0980</td>
    </tr>
  </tbody>
</table>

<table>
  <thead>
    <tr>
      <th colspan=6>
        East Asian
      </th>
    </tr>
    <tr>
      <th>Ingredient</th>
      <th>Prev.</th>
      <th>Pair</th>
      <th>Prev.</th>
      <th>Triplet</th>
      <th>Prev.</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>soy sauce</td>
      <td>0.5404</td>
      <td>garlic, scallion</td>
      <td>0.3537</td>
      <td>garlic, scallion, cayenne</td>
      <td>0.2439</td>
    </tr>
    <tr>
      <td>garlic</td>
      <td>0.5168</td>
      <td>soy sauce, garlic</td>
      <td>0.3370</td>
      <td>soy sauce, garlic, scallion</td>
      <td>0.2309</td>
    </tr>
    <tr>
      <td>scallion</td>
      <td>0.4726</td>
      <td>garlic, cayenne</td>
      <td>0.3091</td>
      <td>garlic, scallion, sesame oil</td>
      <td>0.2050</td>
    </tr>
    <tr>
      <td>cayenne</td>
      <td>0.4004</td>
      <td>soy sauce, scallion</td>
      <td>0.3001</td>
      <td>soy sauce, garlic, sesame oil</td>
      <td>0.2050</td>
    </tr>
    <tr>
      <td>sesame oil</td>
      <td>0.3730</td>
      <td>scallion, cayenne</td>
      <td>0.2757</td>
      <td>soy sauce, scallion, sesame oil</td>
      <td>0.1815</td>
    </tr>
    <tr>
      <td>rice</td>
      <td>0.3376</td>
      <td>garlic, sesame oil</td>
      <td>0.2691</td>
      <td>soy sauce, garlic, cayenne</td>
      <td>0.1815</td>
    </tr>
  </tbody>
</table>

## References

1. Yong-Yeol Ahn, Sebastian E. Ahnert, James P. Bagrow & Albert-László Barabási. Flavor network and the principles of food pairing. Web page retrieved 06/08/2016 at http://www.nature.com/articles/srep00196.
