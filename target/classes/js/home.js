console.log("testirtdtdtd home")

let num = 0;

let displayNum = document.getElementById("displayNum");
displayNum.textContent = num;
function addNum() {
    num++;
    console.log(num);
    displayNum.textContent = num;
}

