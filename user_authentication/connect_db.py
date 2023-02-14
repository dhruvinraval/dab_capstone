
import numpy as np
import pandas as pd
import pyodbc as pyodbc
import face_recognition as fr


cnxn = pyodbc.connect(driver='{SQL Server}', server='DESKTOP-9KO479H\SQLEXPRESS', database='facerecognition',trusted_connection='yes')

cursor = cnxn.cursor()

cursor.execute("SELECT id from driverDetails;") 
for row in cursor:
    print(f'row = {row}')
    print()

