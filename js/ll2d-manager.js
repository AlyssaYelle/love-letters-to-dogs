console.log('connected')
console.log(localStorage.getItem('lastPostcard'))

// Create an instance of a db object for us to store the open database in
let db;

function fetchRandomDog() {
    console.log('we are here at fetchRandomDog')
    fetch('https://dog.ceo/api/breeds/image/random', {


    })
    .then((res) => {
        return res.json();
    })
    .then((res) => {
        localStorage.setItem('currentDog', res.message);
        updateDomWithRandomDog(res.message);
    })
    .catch((err) => {
        updateDomWithRandomDog('https://images.dog.ceo/breeds/hound-blood/n02088466_7486.jpg')
        console.log(err)
    })
}

fetchRandomDog()


function updateDomWithRandomDog(dogImgUrl) {

    dogImg = document.getElementById('random-dog');
    dogImg.src = dogImgUrl;
    document.getElementById('loading-spinner').style.display = 'none';
    dogImg.style.display = 'inline';



}

function displayLuvLetterForm() {

    console.log(localStorage.getItem('currentDog'))
    document.getElementById('form-dog').src = localStorage.getItem('currentDog');
    document.getElementById('random-dog-section').style.display = 'none';
    document.getElementById('luv-letter-form-section').style.display = 'block';
}



function sendLuvLetter() {

    // form = document.getElementById('luv-letter-form-section')
    // name = (document.getElementById('name').value > 0) ? document.getElementById('name').value : 'Anonymous';

    // luvLetter = document.getElementById('luv-letter-input').value

    // postcard = {
    //     'dog': localStorage.getItem('currentDog'),
    //     'admirer': name,
    //     'luvLetter': luvLetter
    // }

    // localStorage.setItem('lastPostcard', postcard)
    // form.reset()
}






window.onload = function() {
    // Open our database; it is created if it doesn't already exist
    // (see onupgradeneeded below)
    let request = window.indexedDB.open('postcards', 1);


    // onerror handler signifies that the database didn't open successfully
    request.onerror = function() {
      console.log('Database failed to open');
    };

    // onsuccess handler signifies that the database opened successfully
    request.onsuccess = function() {
      console.log('Database opened successfully');

      // Store the opened database object in the db variable. This is used a lot below
      db = request.result;

      // Run the displayData() function to display the notes already in the IDB
      //displayPostcards();
    };


    // Setup the database tables if this has not already been done
    request.onupgradeneeded = function(e) {
      // Grab a reference to the opened database
      let db = e.target.result;

      // Create an objectStore to store our notes in (basically like a single table)
      // including a auto-incrementing key
      let objectStore = db.createObjectStore('postcards', { keyPath: 'id', autoIncrement:true });

      // Define what data items the objectStore will contain
      objectStore.createIndex('dog', 'dog', { unique: false });
      objectStore.createIndex('admirer', 'admirer', { unique: false });
      objectStore.createIndex('letter', 'letter', { unique: false });

      console.log('Database setup complete');
    };

    // Create an onsubmit handler so that when the form is submitted the addData() function is run
    form = document.getElementById('ll-form')
    form.onsubmit = addData;

    // add even listeners to menu
    homeButton = document.getElementById('home-button')
    homeButton.onclick = displayHome;
    postcardsButton = document.getElementById('postcards-button')
    postcardsButton.onclick = displayPostcards;


    // Define the addData() function
    function addData(e) {
      // prevent default - we don't want the form to submit in the conventional way
      e.preventDefault();

      // grab the values entered into the form fields and store them in an object ready for being inserted into the DB
      let newItem = { dog: localStorage.getItem('currentDog'), admirer: (document.getElementById('name').value) ? document.getElementById('name').value : 'Anonymous', letter: document.getElementById('luv-letter-input').value };

      // open a read/write db transaction, ready for adding the data
      let transaction = db.transaction(['postcards'], 'readwrite');

      // call an object store that's already been added to the database
      let objectStore = transaction.objectStore('postcards');

      // Make a request to add our newItem object to the object store
      let request = objectStore.add(newItem);
      request.onsuccess = function() {
        // Clear the form, ready for adding the next entry
        document.getElementById('name').value = '';
        document.getElementById('luv-letter-input').value = '';
      };

      // Report on the success of the transaction completing, when everything is done
      transaction.oncomplete = function() {
        console.log('Transaction completed: database modification finished.');
        fetchRandomDog();


        // update the display of data to show the newly added item, by running displayData() again.
        displayPostcards();
      };

      transaction.onerror = function() {
        console.log('Transaction not opened due to error');
      };
    }


    // Define the displayHome() fn
    function displayHome() {
        document.getElementById('postcard-display').style.display = 'none';
        document.getElementById('luv-letter-form-section').style.display = 'none';
        document.getElementById('random-dog-section').style.display = 'block';       
    }

    // Define the displayData() function
    function displayPostcards() {

        document.getElementById('random-dog-section').style.display = 'none';
        document.getElementById('luv-letter-form-section').style.display = 'none'
        list = document.getElementById('postcard-display')
        list.style.display = 'block';
      // Here we empty the contents of the list element each time the display is updated
      // If you didn't do this, you'd get duplicates listed each time a new note is added
      while (list.firstChild) {
        list.removeChild(list.firstChild);
      }

      // Open our object store and then get a cursor - which iterates through all the
      // different data items in the store
      let objectStore = db.transaction('postcards').objectStore('postcards');
      objectStore.openCursor().onsuccess = function(e) {
        // Get a reference to the cursor
        let cursor = e.target.result;

        // If there is still another data item to iterate through, keep running this code
        if(cursor) {
          // Create a list item, h3, and p to put each data item inside when displaying it
          // structure the HTML fragment, and append it inside the list
          const listItem = document.createElement('article');

          const dogImgContainer = document.createElement('div');
          dogImgContainer.classList.add('postcard-img')
          const dogPicture = document.createElement('img');
          dogImgContainer.appendChild(dogPicture);

          const luvLetterContainer = document.createElement('div')
          luvLetterContainer.classList.add('postcard-ll')
          const h3 = document.createElement('h3');
          const para = document.createElement('p');
          luvLetterContainer.appendChild(h3);
          luvLetterContainer.appendChild(para);

          listItem.appendChild(dogImgContainer);
          listItem.appendChild(luvLetterContainer)
          list.appendChild(listItem);

          // Put the data from the cursor inside the h3 and para
          dogPicture.src = cursor.value.dog;
          h3.textContent = `A luv letter from ${cursor.value.admirer}`;
          para.textContent = cursor.value.letter;

          // Store the ID of the data item inside an attribute on the listItem, so we know
          // which item it corresponds to. This will be useful later when we want to delete items
          listItem.setAttribute('data-note-id', cursor.value.id);


          // Iterate to the next item in the cursor
          cursor.continue();
        } else {
          // Again, if list item is empty, display a 'No notes stored' message
          if(!list.firstChild) {
            const listItem = document.createElement('h3');
            listItem.textContent = 'No postcards stored.';
            list.appendChild(listItem);
          }
          // if there are no more cursor items to iterate through, say so
          console.log('Notes all displayed');
        }
      };
    }
};














