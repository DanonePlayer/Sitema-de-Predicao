import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TelaDashboardCoordComponent } from './tela-dashboard-coord.component';


describe('TelaDashboardCoordComponent', () => {
  let component: TelaDashboardCoordComponent;
  let fixture: ComponentFixture<TelaDashboardCoordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaDashboardCoordComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaDashboardCoordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
