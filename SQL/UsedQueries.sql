-- Select personal information and role for users with login and student ID 2
SELECT
    p.PersonID,
    p.FirstName,
    p.LastName,
    p.Birthdate,
    p.Address,
    L.Email,
    L.Password, -- Consider security implications of including password in results
    p.Gender,
    Pho.Phone,
    DATEDIFF(MONTH, Birthdate, GETDATE()) / 12 AS Age,
    CASE
        WHEN s.StudentID IS NOT NULL THEN 'Student'
        WHEN pr.ProfID IS NOT NULL THEN 'Professor'
        END AS Role
FROM Person p
    LEFT JOIN Student s ON p.PersonID = s.StudentID
    LEFT JOIN Professor pr ON p.PersonID = pr.ProfID
    LEFT JOIN Login L ON p.PersonID = L.PersonID
    LEFT JOIN Phone Pho ON p.PersonID = Pho.PersonID
WHERE p.PersonID IN (
    SELECT PersonID FROM Login WHERE StudentID = 2
);

-- Calculate average GPA for student ID 5
SELECT p1.UniversityID, AVG(p2.GPA_Value) AS GPA
FROM Student p1
LEFT JOIN Enrollment p2 ON p1.StudentID = p2.StudentID
WHERE p1.StudentID = 5
GROUP BY p1.StudentID, p1.UniversityID;

-- Select all data from tables
SELECT * FROM Student;
SELECT * FROM Professor;
SELECT * FROM Person;
SELECT * FROM Login;
SELECT * FROM Phone;
SELECT * FROM Course;
SELECT * FROM Enrollment;