# SacomDemo

SACOM is an electronic retailer that has multiple online and physical stores. SACOM needs a
middleware application that can integrate the existing different ordering systems with the
suppliers of electronic products.

SACOM customers place product orders from various locations and systems and these orders
need to be processed and sent to the corresponding suppliers.

The orders are sent as XML files on a central server, where your application needs to process them
and create the XML files that will be sent to the suppliers.

Each XML file that contains orders will be processed into multiple XML files, one file per each
supplier. The supplier output file will contain all the supplier’s products from one orders file.

Each product that will be sent to the supplier must also contain the order ID, so that it will be easily tracked to
the original order.

The application will wait for the orders files in one folder and will export the supplier files in a
separate folder. Once a file that contains orders is received in the specific folder, the application
will process it, and then it will wait for another orders file to process.

Once a customer places an order, he expects it to be delivered as soon as possible, so you must
focus on the application XML files processing performance in order to keep the customers happy.
