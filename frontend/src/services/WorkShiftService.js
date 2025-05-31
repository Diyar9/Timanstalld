import axios from 'axios';
import WorkShiftComponent from "../components/WorkShiftComponent";

const WORKSHIFT_REST_API_URL = 'http://localhost:8080/api/workshift';

export const listWorkShifts = () => axios.get(WORKSHIFT_REST_API_URL);

export const createWorkShift = (workShift) => axios.post(WORKSHIFT_REST_API_URL, workShift);

export const getWorkShift = (workShiftId) => axios.get(WORKSHIFT_REST_API_URL + '/' + workShiftId);

export const updateWorkShift = (workShiftId, workShift) => axios.put(WORKSHIFT_REST_API_URL + '/' + workShiftId, workShift);

export const deleteWorkShift = (workShiftId) => axios.delete(WORKSHIFT_REST_API_URL + '/' + workShiftId);

export const totalSalary = () => axios.get(WORKSHIFT_REST_API_URL + '/salary/total');
