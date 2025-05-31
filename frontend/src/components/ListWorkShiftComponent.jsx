import React, {useEffect, useState} from 'react'
import {deleteWorkShift, listWorkShifts, totalSalary} from "../services/WorkShiftService";
import { useNavigate} from "react-router-dom";

const ListWorkShiftComponent = () => {

    const [workshifts, setWorkShifts] = useState([])
    const [salary, setSalary] = useState(0);
    const navigator = useNavigate();

    useEffect(() => {
        getAllWorkShifts();
        getTotalSalary();
    }, [])

    function getAllWorkShifts() {
        listWorkShifts().then((response) => {
            setWorkShifts(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function getTotalSalary() {
        totalSalary().then((response) => {
            setSalary(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function addNewWorkShift() {
        navigator('/add-workshift')
    }

    function updateWorkShift(id) {
        navigator(`/edit-workshift/${id}`)
    }

    function removeWorkShift(id) {
        console.log(id);
        deleteWorkShift(id).then((response) => {
            getAllWorkShifts();
            getTotalSalary();
        }).catch(error => {
            console.error(error)
        })
    }

    return (
        <div className='container'>
            <h2 className='text-center'>List of Work Shifts</h2>
            <button type="button" className="btn btn-primary" onClick={addNewWorkShift}>Add Work Shift</button>
            <table className={"table table-striped table-bordered"}>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Work date</th>
                    <th>Start time</th>
                    <th>End time</th>
                    <th>Break minutes</th>
                    <th>Note</th>
                    <th>Salary</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {
                    workshifts.map(workshift =>
                        <tr key={workshift.id}>
                            <td>{workshift.id}</td>
                            <td>{workshift.workDate}</td>
                            <td>{workshift.startTime}</td>
                            <td>{workshift.endTime}</td>
                            {/*<td>{workshift.totalHours}</td>*/}
                            <td>{workshift.breakMinutes}</td>
                            {/*            <td>{workshift.baseHours}</td>
                                    <td>{workshift.ob50Hours}</td>
                                    <td>{workshift.ob70Hours}</td>
                                    <td>{workshift.ob100Hours}</td>
                                    <td>{workshift.weekend.toString()}</td> */}
                            <td>{workshift.note}</td>
                            <td>{workshift.salary.toFixed(2) + ' kr'}</td>
                            <td>
                                <button className='btn btn-info'
                                        onClick={() => updateWorkShift(workshift.id)}>Update
                                </button>
                                <button className='btn btn-danger'
                                        onClick={() => removeWorkShift(workshift.id)}>Delete
                                </button>
                            </td>
                        </tr>
                    )
                }
                </tbody>
                <div>
                    <h2>Total Salary</h2>
                    <p>{salary.toFixed(2)} kr</p>
                </div>
            </table>
        </div>
    )
}

export default ListWorkShiftComponent