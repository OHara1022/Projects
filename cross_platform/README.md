# README #

This application was build to show the use of one databse on both platforms writen natively.

*Info for testing

You may create your own account or login using my credentials below. Password must be six characters
You can add, view, and delete contact created. All data is saving and retrieving from firebase database.

When testing for iOS you can use the same login credentials that are listed or the one your created last week. 
You may also create a new user and that user will add to the same database. 

**Login credentials:
email: ohara@test.com
password: password

**BUG/ERROR
iOS:
When deleteing on row of the table view the application will crash with a NSExcepetion. I have logs and the
code that removes the item from the list are in the ContactTableViewController lines 113 thru 141. It logs out
that the item is removed but still returns me a NSExection. Please let me know where I went wrong with removing
from my list locally being I was able to remove it remotely and on rebuild display proper data.

Android:
When deleting on emulator I am receving a getSystemService on a null object reference error, I was not
receiving this error until I enable persitant storage with firebase. I am also receiving the same error message
when I try and sign out a user. This occurs weather you are connected to a network or not. I do not have my
actual device to test on, I know when testing on device last month I did not receive those errors but wanted
let it be known these errors occur while testing on emulator Nexus 5x.

On the return from editing contact information the details screen does not show the updated data, once you
return to the list the list shows proper data and on reselection of that contact proper data is displayed.

**Network and Validation Week_4:
iOS: 
When editing a selected contact from the list the application creates a new contact and does not update the
currently selected contact. I was unable to retrieve the key propely to update the selected contact or I have
the code in the wrong location for updating the exsiting contact. I used the proper firebase methods but feel
they were in wrong location or when I retrieve the key is wrong on contact selection.
