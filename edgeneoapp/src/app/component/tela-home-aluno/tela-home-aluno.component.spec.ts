import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaHomeAlunoComponent } from './tela-home-aluno.component';

describe('TelaHomeAlunoComponent', () => {
  let component: TelaHomeAlunoComponent;
  let fixture: ComponentFixture<TelaHomeAlunoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaHomeAlunoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaHomeAlunoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
