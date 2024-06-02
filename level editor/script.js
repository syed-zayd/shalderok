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

function markVoidTiles(level) {
    const rows = level.length;
    const cols = level[0].length;
    const voidTiles = Array.from({ length: rows }, () => Array(cols).fill(false));
    
    // Function to perform flood fill from the edges
    function floodFill(x, y) {
        const queue = [];
        queue.push([x, y]);
        
        while (queue.length > 0) {
            const [cx, cy] = queue.shift();
            
            if (cx < 0 || cx >= cols || cy < 0 || cy >= rows) {
                continue;
            }
            
            if (level[cy][cx] !== 'E' || voidTiles[cy][cx]) {
                continue;
            }
            
            voidTiles[cy][cx] = true;
            
            queue.push([cx + 1, cy]);
            queue.push([cx - 1, cy]);
            queue.push([cx, cy + 1]);
            queue.push([cx, cy - 1]);
        }
    }

    // Perform flood fill from the edges
    for (let i = 0; i < rows; i++) {
        if (level[i][0] === 'E') floodFill(0, i);
        if (level[i][cols - 1] === 'E') floodFill(cols - 1, i);
    }
    for (let i = 0; i < cols; i++) {
        if (level[0][i] === 'E') floodFill(i, 0);
        if (level[rows - 1][i] === 'E') floodFill(i, rows - 1);
    }
    
    // Update the level with void tiles
    for (let y = 0; y < rows; y++) {
        for (let x = 0; x < cols; x++) {
            if (level[y][x] === 'E' && !voidTiles[y][x]) {
                level[y][x] = 'V';
            }
        }
    }
    
    return level;
}

function saveLevel() {
    const content = `${cols}\n${rows}\n` + markVoidTiles(level).map(row => row.join('')).join('\n');
    const blob = new Blob([content], { type: 'text/plain' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'level.txt';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
}

function copyLevel() {
    navigator.clipboard.writeText(`${cols}\n${rows}\n` + markVoidTiles(level).map(row => row.join('')).join('\n'));
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
