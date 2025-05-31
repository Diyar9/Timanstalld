import React, {useEffect, useState} from 'react';
import {createWorkShift, getWorkShift, updateWorkShift} from "../services/WorkShiftService";
import { useNavigate, useParams } from "react-router-dom";

const WorkShiftComponent = () => {
    const [workDate, setWorkDate] = useState('');
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [breakMinutes, setBreakMinutes] = useState('');
    const [note, setNote] = useState('');
    const [salary, setsalary] = useState('');

    const {id} = useParams();
    const [errors, setErrors] = useState({
        workDate: '',
        startTime: '',
        endTime: '',
        breakMinutes: '',
        note: '',
        salary: ''
    })

    const navigator = useNavigate();

    useEffect(() => {

        if(id){
            getWorkShift(id).then((response) => {
                setWorkDate(response.data.workDate);
                setStartTime(response.data.startTime);
                setEndTime(response.data.endTime);
                setBreakMinutes(response.data.breakMinutes);
                setNote(response.data.note);
                setsalary(response.data.salary);
            }).catch(error => {
                console.error(error);
            })
        }

    }, [id])

    function saveOrUpdateWorkShift(e) {
        e.preventDefault();

        const workShift = {
            workDate,
            startTime,
            endTime,
            breakMinutes,
            note,
            salary
        };
        console.log('Sparad data:', workShift);

        if(validateForm()){

            if(id){
                updateWorkShift(id, workShift).then((response) => {
                    console.log(response.data);
                    navigator('/workshifts')
                }).catch(error => {
                    console.error(error)
                })
            } else {
                createWorkShift(workShift).then((response) => {
                    console.log(response.data);
                    navigator('/workshifts')
                }).catch(error => {
                    console.error(error);
                })
            }
        }
    }

    function validateForm() {
        let valid = true;
        const errorsCopy = { ...errors };

        if (workDate.trim()) {
            errorsCopy.workDate = '';
        } else {
            errorsCopy.workDate = 'Work Date is required';
            valid = false;
        }

        if (startTime.trim()) {
            errorsCopy.startTime = '';
        } else {
            errorsCopy.startTime = 'Start Time is required';
            valid = false;
        }

        if (endTime.trim()) {
            errorsCopy.endTime = '';
        } else {
            errorsCopy.endTime = 'End Time is required';
            valid = false;
        }

        if (breakMinutes && !isNaN(breakMinutes) && Number(breakMinutes) >= 0) {
            errorsCopy.breakMinutes = '';
        } else {
            errorsCopy.breakMinutes = 'Break Minutes must be a non-negative number';
            valid = false;
        }

        if (note.length <= 200) {
            errorsCopy.note = '';
        } else {
            errorsCopy.note = 'Note must be 200 characters or less';
            valid = false;
        }

        setErrors(errorsCopy);
        return valid;
    }

    function pageTitle() {
        if(id) {
            return <h2 className='text-center'>Update Work Shift</h2>
        } else {
            <h2 className='text-center'>Add Work Shift</h2>
        }
    }

    return (
        <div className='container'>
            <br/><br/>
            <div className='row'>
                <div className='card col-md-6 offset-md-3 offset-md-3'>
                    {
                        pageTitle()
                    }
                    <div className='card-body'>
                        <form>
                            <div className='form-group mb-2'>
                                <label className='form-label'>Work Date:</label>
                                <input type='date' name='workDate' value={workDate}
                                       className={`form-control ${errors.workDate ? 'is-invalid' : ''}`}
                                       onChange={(e) => setWorkDate(e.target.value)}/>
                                {errors.workDate && <div className='invalid-feedback'> {errors.workDate} </div>}
                            </div>

                            <div className='form-group mb-2'>
                                <label className='form-label'>Start Time:</label>
                                <input type='time' name='startTime' value={startTime}
                                       className={`form-control ${errors.startTime ? 'is-invalid' : ''}`}
                                       onChange={(e) => setStartTime(e.target.value)}/>
                            </div>

                            <div className='form-group mb-2'>
                                <label className='form-label'>End Time:</label>
                                <input type='time' name='endTime' value={endTime}
                                       className={`form-control ${errors.endTime ? 'is-invalid' : ''}`}
                                       onChange={(e) => setEndTime(e.target.value)}/>
                                {errors.endTime && <div className='invalid-feedback'> {errors.endTime} </div>}
                            </div>

                            <div className='form-group mb-2'>
                                <label className='form-label'>Break Minutes:</label>
                                <input type='number' name='breakMinutes' value={breakMinutes}
                                       className={`form-control ${errors.breakMinutes ? 'is-invalid' : ''}`}
                                       onChange={(e) => setBreakMinutes(e.target.value)}/>
                                {errors.breakMinutes && <div className='invalid-feedback'> {errors.breakMinutes} </div>}
                            </div>

                            <div className='form-group mb-2'>
                                <label className='form-label'>Note:</label>
                                <input type='text' placeholder='Enter a note for the shift' name='note' value={note}
                                       className={`form-control ${errors.note ? 'is-invalid' : ''}`}
                                       onChange={(e) => setNote(e.target.value)}/>
                                {errors.note && <div className='invalid-feedback'> {errors.note} </div>}
                            </div>

                            <button className='btn btn-success' onClick={saveOrUpdateWorkShift}>Submit</button>
                        </form>
                    </div>
                </div>
            </div>
            <br/><br/>
        </div>
    );
};

export default WorkShiftComponent;
