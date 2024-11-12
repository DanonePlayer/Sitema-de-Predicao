import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TelaDashboardAlunoComponent } from './tela-dashboard-aluno.component';

describe('TelaDashboardComponent', () => {
  let component: TelaDashboardAlunoComponent;
  let fixture: ComponentFixture<TelaDashboardAlunoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaDashboardAlunoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaDashboardAlunoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
