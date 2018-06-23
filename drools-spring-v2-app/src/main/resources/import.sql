
insert into user (id, username, password, first_name, last_name, email, category) values (1, 'admin', '123', 'Ana', 'Lazarevic', 'ana@gmail.com', 0);

insert into user (id, username, password, first_name, last_name, email, category) values (2, 'neca', '123', 'Nevena', 'Lazarevic', 'necapereca@gmail.com', 1);

insert into user (id, username, password, first_name, last_name, email, category) values (3, 'zeks', '123', 'Zeljko', 'Crnoglavac', 'zeljko@gmail.com', 1);

insert into user (id, username, password, first_name, last_name, email, category) values (4, 'nemanja', '123', 'Nemanja', 'Mitrovic', 'nemanja@gmail.com', 1);

insert into illness (id, name, category) values (1, 'Cold', 0);
insert into illness (id, name, category) values (2, 'Fever', 0);
insert into illness (id, name, category) values (3, 'Tonsil Inflamation', 0);
insert into illness (id, name, category) values (4, 'Sinus Infection', 0);
insert into illness (id, name, category) values (5, 'Hypertension', 1);
insert into illness (id, name, category) values (6, 'Diabetes', 1);
insert into illness (id, name, category) values (7, 'Chronic Kidney Disease', 2);
insert into illness (id, name, category) values (8, 'Acute Kidney Injury', 2);

-- Cold
insert into symptom (id, term, helper, specifics, illness_id) values (1, 0, 'Runny nose', 0, 1);
insert into symptom (id, term, helper, specifics, illness_id) values (2, 1, 'Sore throat', 0, 1);
insert into symptom (id, term, helper, specifics, illness_id) values (3, 2, 'Headache', 0, 1);
insert into symptom (id, term, helper, specifics, illness_id) values (4, 3, 'Sneeze', 0, 1);
insert into symptom (id, term, helper, specifics, illness_id) values (5, 4, 'Cough', 0, 1);

-- Fever
insert into symptom (id, term, helper, specifics, illness_id) values (6, 3, 'Sneeze', 0, 2);
insert into symptom (id, term, helper, specifics, illness_id) values (7, 1, 'Sore throat', 0, 2);
insert into symptom (id, term, helper, specifics, illness_id) values (8, 4, 'Cough', 0, 2);
insert into symptom (id, term, helper, specifics, illness_id) values (9, 5, 'Temperature over 38', 0, 2);
insert into symptom (id, term, helper, specifics, illness_id) values (10, 0, 'Runny nose', 0, 2);
insert into symptom (id, term, helper, specifics, illness_id) values (11, 2, 'Headache', 0, 2);
insert into symptom (id, term, helper, specifics, illness_id) values (12, 6, 'Shiver', 0, 2);

-- Tonsil Inflamation
insert into symptom (id, term, helper, specifics, illness_id) values (13, 1, 'Sore throat', 0, 3);
insert into symptom (id, term, helper, specifics, illness_id) values (14, 7, 'Pain spread to ears', 0, 3);
insert into symptom (id, term, helper, specifics, illness_id) values (15, 2, 'Headache', 0, 3);
insert into symptom (id, term, helper, specifics, illness_id) values (16, 8, 'Temperature between 40 and 41', 0, 3);
insert into symptom (id, term, helper, specifics, illness_id) values (17, 6, 'Shiver', 0, 3);
insert into symptom (id, term, helper, specifics, illness_id) values (18, 9, 'Apetite loss', 0, 3);
insert into symptom (id, term, helper, specifics, illness_id) values (19, 10, 'Tiredness', 0, 3);
insert into symptom (id, term, helper, specifics, illness_id) values (20, 11, 'Yellow secretion from nose', 0, 3);

-- Sinus Infection
insert into symptom (id, term, helper, specifics, illness_id) values (21, 12, 'Eye swelling', 0, 4);
insert into symptom (id, term, helper, specifics, illness_id) values (22, 2, 'Headache', 0, 4);
insert into symptom (id, term, helper, specifics, illness_id) values (23, 11, 'Yellow secretion from nose', 0, 4);
insert into symptom (id, term, helper, specifics, illness_id) values (24, 1, 'Sore throat', 0, 4);
insert into symptom (id, term, helper, specifics, illness_id) values (25, 5, 'Temperature over 38', 0, 4);
insert into symptom (id, term, helper, specifics, illness_id) values (26, 4, 'Cough', 0, 4);
insert into symptom (id, term, helper, specifics, illness_id) values (27, 25, 'Suffered from Cold/Fever in last 60 days', 1, 4);

-- Hypertension
insert into symptom (id, term, helper, specifics, illness_id) values (28, 26, 'Ten times high pressure in last 6 months', 0, 5);

-- Diabetes
insert into symptom (id, term, helper, specifics, illness_id) values (29, 14, 'Often urination', 0, 6);
insert into symptom (id, term, helper, specifics, illness_id) values (30, 15, 'Weight loss', 0, 6);
insert into symptom (id, term, helper, specifics, illness_id) values (31, 16, 'Fatigue', 0, 6);
insert into symptom (id, term, helper, specifics, illness_id) values (32, 17, 'Nausea and vomitting', 0, 6);

-- Chronic Kidney Disease
insert into symptom (id, term, helper, specifics, illness_id) values (33, 16, 'Fatigue urination', 0, 7);
insert into symptom (id, term, helper, specifics, illness_id) values (34, 18, 'Nocturia', 0, 7);
insert into symptom (id, term, helper, specifics, illness_id) values (35, 19, 'Legs and joint swelling', 0, 7);
insert into symptom (id, term, helper, specifics, illness_id) values (36, 20, 'Choking', 0, 7);
insert into symptom (id, term, helper, specifics, illness_id) values (37, 21, 'Chest pain', 0, 7);
insert into symptom (id, term, helper, specifics, illness_id) values (38, 27, 'Sufferes from Hypertension more than 6 months', 1, 7);
insert into symptom (id, term, helper, specifics, illness_id) values (39, 28, 'Sufferes from Diabetes', 1, 7);

-- Acute Kidney Injury
insert into symptom (id, term, helper, specifics, illness_id) values (40, 24, 'Recovers from surgery', 1, 8);
insert into symptom (id, term, helper, specifics, illness_id) values (41, 16, 'Fatigue', 0, 8);
insert into symptom (id, term, helper, specifics, illness_id) values (42, 20, 'Choking', 0, 8);
insert into symptom (id, term, helper, specifics, illness_id) values (43, 19, 'Legs and joint swelling', 0, 8);
insert into symptom (id, term, helper, specifics, illness_id) values (44, 22, 'Diarrhea', 0, 8);
insert into symptom (id, term, helper, specifics, illness_id) values (46, 29, 'Suffered from illness with high temp as symptom in last 2 weeks', 1, 8);
insert into symptom (id, term, helper, specifics, illness_id) values (47, 30, 'Used antibiotics in last 3 weeks', 1, 8);