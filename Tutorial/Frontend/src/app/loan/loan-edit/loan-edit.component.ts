import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LoanService } from '../loan.service';
import { Loan } from '../model/Loan';
import { Client } from 'src/app/client/model/Client';
import { Game } from 'src/app/game/model/Game';
import { GameService } from 'src/app/game/game.service';
import { ClientService } from 'src/app/client/client.service';

@Component({
  selector: 'app-loan-edit',
  templateUrl: './loan-edit.component.html',
  styleUrls: ['./loan-edit.component.scss']
})
export class LoanEditComponent {
  loan: Loan;
  clients: Client[];
  games: Game[];
  

  constructor(
    public dialogRef: MatDialogRef<LoanEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private loanService: LoanService,
    private gameService: GameService,
    private clientService: ClientService
  ) { }

  ngOnInit() {
      this.loan = new Loan();
      this.gameService.getGames().subscribe(games => this.games = games);
      this.clientService.getClients().subscribe(clients => this.clients = clients);
  }

  onSave() {
    if(this.loan.initDate) {
      this.loan.initDate = this.convertDate(new Date(this.loan.initDate));
    }

    if(this.loan.endDate) {
      this.loan.endDate = this.convertDate(new Date(this.loan.endDate));
    }

    this.loanService.saveLoan(this.loan).subscribe(result => {
      this.dialogRef.close(result);
    });
  }
  
  onClose() {
    this.dialogRef.close();
  }

  convertDate(date: Date): string {
    const offset = date.getTimezoneOffset() * 60 * 1000;
    const adjusted = new Date(date.getTime() - offset);

    return adjusted.toISOString().split('T')[0];
  }
}
