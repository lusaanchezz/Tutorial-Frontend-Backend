import { LoanPage } from "./LoanPage";

export const LOAN_DATA: LoanPage = {
    content: [
        {id: 1, game: { id: 1, title: 'Juego 1', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } }, client: { id: 1, name: 'Cliente1' }, initDate: '2024-11-11', endDate: '2024-11-15'},
        {id: 2, game: { id: 2, title: 'Juego 2', age: 7, category: { id: 2, name: 'Categoría 2' }, author: { id: 2, name: 'Autor 2', nationality: 'Nacionalidad 2' } }, client: { id: 2, name: 'Cliente2' }, initDate: '2024-11-12', endDate: '2024-11-16'},
        {id: 3, game: { id: 3, title: 'Juego 3', age: 8, category: { id: 3, name: 'Categoría 3' }, author: { id: 3, name: 'Autor 3', nationality: 'Nacionalidad 3' } }, client: { id: 3, name: 'Cliente3' }, initDate: '2024-11-13', endDate: '2024-11-17'},
        {id: 4, game: { id: 4, title: 'Juego 4', age: 9, category: { id: 4, name: 'Categoría 4' }, author: { id: 4, name: 'Autor 4', nationality: 'Nacionalidad 4' } }, client: { id: 4, name: 'Cliente4' }, initDate: '2024-11-14', endDate: '2024-11-18'},
        {id: 5, game: { id: 5, title: 'Juego 5', age: 5, category: { id: 5, name: 'Categoría 5' }, author: { id: 5, name: 'Autor 5', nationality: 'Nacionalidad 5' } }, client: { id: 5, name: 'Cliente5' }, initDate: '2024-11-15', endDate: '2024-11-19'},
        {id: 6, game: { id: 6, title: 'Juego 6', age: 4, category: { id: 6, name: 'Categoría 6' }, author: { id: 6, name: 'Autor 6', nationality: 'Nacionalidad 6' } }, client: { id: 6, name: 'Cliente6' }, initDate: '2024-11-16', endDate: '2024-11-20'},
    ],
    pageable: {
        pageSize: 5,
        pageNumber: 0,
        sort: [
            {property: "id", direction: "ASC"}
        ]
    },
    totalElements: 6
}