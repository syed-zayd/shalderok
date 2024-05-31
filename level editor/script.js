const grid = document.getElementById('grid');
const saveButton = document.getElementById('saveButton');
const copyButton = document.getElementById('copyButton');
const makeWallsButton = document.getElementById('makeWallsButton');
const makeEmptyButton = document.getElementById('makeEmptyButton');
const makeBordersButton = document.getElementById('makeBordersButton');
const controlsForm = document.getElementById('controls');
const rowsInput = document.getElementById('rowsInput');
const colsInput = document.getElementById('colsInput');
const liveTextarea = document.getElementById('liveTextarea');
let rows;
let cols;

let level = [];

controlsForm.addEventListener('submit', (event) => {
    event.preventDefault();
    generateGrid();
});

saveButton.addEventListener('click', saveLevel);
copyButton.addEventListener('click', copyLevel);
makeWallsButton.addEventListener('click', makeWalls);
makeEmptyButton.addEventListener('click', makeEmpty);
makeBordersButton.addEventListener('click', makeBorders);

function generateGrid(fill="E") {
    rows = parseInt(rowsInput.value);
    cols = parseInt(colsInput.value);
    level = Array.from({ length: rows }, () => Array(cols).fill(fill));

    grid.innerHTML = '';
    grid.style.gridTemplateColumns = `repeat(${cols}, 30px)`;

    for (let r = 0; r < rows; r++) {
        for (let c = 0; c < cols; c++) {
            const cell = document.createElement('div');
            cell.className = 'cell ';
            if (fill === "E") {
                cell.className += 'empty';
            } else if (fill === "W") {
                cell.className += 'wall';
            }
            cell.dataset.row = r;
            cell.dataset.col = c;
            cell.addEventListener('click', toggleCell);
            grid.appendChild(cell);
        }
    }

    updateLiveTextarea();
}

function toggleCell(event) {
    const cell = event.target;
    const row = cell.dataset.row;
    const col = cell.dataset.col;

    if (level[row][col] === 'E') {
        level[row][col] = 'W';
        cell.className = 'cell wall';
    } else {
        level[row][col] = 'E';
        cell.className = 'cell empty';
    }

    updateLiveTextarea();
}

function saveLevel() {
    const content = `${cols}\n${rows}\n` + level.map(row => row.join('')).join('\n');
    const blob = new Blob([content], { type: 'text/plain' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'level.txt';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
}

function copyLevel() {
    navigator.clipboard.writeText(`${cols}\n${rows}\n` + level.map(row => row.join('')).join('\n'));
}

function makeWalls() {
    generateGrid("W");
}

function makeEmpty() {
    generateGrid("E");
}

function makeBorders() {
    rows = parseInt(rowsInput.value);
    cols = parseInt(colsInput.value);

    grid.innerHTML = '';
    grid.style.gridTemplateColumns = `repeat(${cols}, 30px)`;

    for (let r = 0; r < rows; r++) {
        for (let c = 0; c < cols; c++) {
            const cell = document.createElement('div');
            cell.className = 'cell ';
            if (r===0 || c===0 || r===rows-1 || c===cols-1) {
                cell.className += 'wall';
                level[r][c] = "W";
            } else {
                cell.className += 'empty';
                level[r][c] = "E";
            }
            cell.dataset.row = r;
            cell.dataset.col = c;
            cell.addEventListener('click', toggleCell);
            grid.appendChild(cell);
        }
    }

    updateLiveTextarea();
}

function updateLiveTextarea() {
    liveTextarea.innerHTML = `${cols}\n${rows}\n` + level.map(row => row.join('')).join('\n');
    console.log("a");
    liveTextarea.rows = rows+2;
    liveTextarea.cols = cols;
}

generateGrid();
