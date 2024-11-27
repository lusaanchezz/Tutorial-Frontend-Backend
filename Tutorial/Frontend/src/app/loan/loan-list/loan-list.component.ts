import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { LoanEditComponent } from '../loan-edit/loan-edit.component';
import { LoanService } from '../loan.service';
import { Loan } from '../model/Loan';
import { Game } from 'src/app/game/model/Game';
import { Client } from 'src/app/client/model/Client';
import { GameService } from 'src/app/game/game.service';
import { ClientService } from 'src/app/client/client.service';
import { LoanSearchDto } from '../model/LoanSearchDto';

@Component({
  selector: 'app-loan-list',
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.scss']
})
export class LoanListComponent implements OnInit {
  games: Game[];
  clients: Client[];
  loans: Loan[];

  filterGame: Game;
  filterClient: Client;
  filterDate: Date;

  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = ['id', 'game', 'client', 'initDate', 'endDate', 'action'];

  constructor(
    private loanService: LoanService,
    private gameService: GameService,
    private clientService: ClientService,
    public dialog: MatDialog, ) { }

    ngOnInit(): void {
      this.gameService.getGames().subscribe(games => this.games = games);
      this.clientService.getClients().subscribe(clients => this.clients = clients);
      this.loadPage();
    }

    loadPage(event?: PageEvent) {
      let pageable = {
        pageNumber: this.pageNumber,
        pageSize: this.pageSize,
      };
    
      console.log("event a: " + event);
      if (event) {
        console.log("event: " + event);
        pageable.pageSize = event.pageSize;
        pageable.pageNumber = event.pageIndex;
      }
    
      const searchDto: LoanSearchDto = {
        gameId: this.filterGame != null ? this.filterGame.id : null,
        clientId: this.filterClient != null ? this.filterClient.id : null,
        searchDate: this.filterDate ? this.filterDate.toISOString().split('T')[0] : null,
        pageable: pageable,
      };
    
      console.log('Search DTO:', searchDto);
    
      //console.log("getLoans: " + this.loanService.getLoans(searchDto));
      this.loanService.getLoans(searchDto).subscribe(data => {
        this.dataSource.data = data.content;
        this.pageNumber = data.pageable.pageNumber;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
        console.log('Data received:', data);
      });
    }

  createLoan() {
    const dialogRef = this.dialog.open(LoanEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  deleteLoan(loan: Loan) {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: {title: "Eliminar préstamo", description: "Atención, si borra el préstamo se perderán los datos. <br> ¿Desea eliminar el préstamo?"}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.loanService.deleteLoan(loan.id).subscribe(result => {
          this.ngOnInit();
        });
      }
    });
  }

  onCleanFilter(): void {
    this.filterGame = null;
    this.filterClient = null;
    this.filterDate = null;

    this.onSearch();
  }

  onSearch(): void {
    this.loadPage();
  }
}
